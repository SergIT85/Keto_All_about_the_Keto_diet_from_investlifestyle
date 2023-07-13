package ru.investlifestyle.app.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import java.lang.Exception
import ru.investlifestyle.app.data.PostMapper
import ru.investlifestyle.app.data.networkApi.PostsApiInterface
import ru.investlifestyle.app.data.room.PostDaoRoom
import ru.investlifestyle.app.data.room.PostDbModelEntity

@ExperimentalPagingApi
class PostPagingRemoteMediator(
    private val postDaoRoom: PostDaoRoom,
    private val postsApiInterface: PostsApiInterface,
    private val mapper: PostMapper
) : RemoteMediator<Int, PostDbModelEntity>() {

    private var pageIndex = 1

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PostDbModelEntity>
    ): MediatorResult {

        pageIndex = getPageIndex(loadType)
            ?: return MediatorResult.Success(endOfPaginationReached = true)

        val limit = state.config.pageSize
        val offset = pageIndex * limit

        return try {
            val postList = getPostList(pageIndex)
            if (loadType == LoadType.REFRESH) {
                postDaoRoom.transaction(postList)
            } else {
                postDaoRoom.save(postList)
            }
            MediatorResult.Success(
                endOfPaginationReached = postList.size < limit
            )
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private fun getPageIndex(loadType: LoadType): Int? {
        pageIndex = when (loadType) {
            LoadType.REFRESH -> 1
            LoadType.PREPEND -> return null
            LoadType.APPEND -> ++pageIndex
        }
        return pageIndex
    }

    private suspend fun getPostList(page: Int): List<PostDbModelEntity> {
        return mapper.listPostModelToListDbModel(postsApiInterface.getPostsList(page))
    }

    interface Factory {
        fun create(): PostPagingRemoteMediator
    }
}
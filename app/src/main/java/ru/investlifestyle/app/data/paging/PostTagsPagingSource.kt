package ru.investlifestyle.app.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import java.io.IOException
import javax.inject.Inject
import retrofit2.HttpException
import ru.investlifestyle.app.data.PostMapper
import ru.investlifestyle.app.data.networkApi.PostsApiInterface
import ru.investlifestyle.app.ui.models.PostUiModel

class PostTagsPagingSource @Inject constructor(
    private val apiClient: PostsApiInterface,
    private val mapper: PostMapper,
    private val tagsId: Int,
) : PagingSource<Int, PostUiModel>() {
    override fun getRefreshKey(state: PagingState<Int, PostUiModel>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PostUiModel> {
        return try {

            val position = params.key ?: 1
            val post = apiClient.loadSubjectTagsPosts(
                tags = tagsId,
                page = position,
                perPage = params.loadSize,
                embed = true
            )

            return LoadResult.Page(
                data = mapper.mapListPostDataToListPostUi(post),
                prevKey = if (position == WORDPRESS_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (post.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    companion object {
        private const val WORDPRESS_STARTING_PAGE_INDEX = 1
    }
}

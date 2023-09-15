package ru.investlifestyle.app.data.repository

import androidx.paging.*
import javax.inject.Inject
import kotlinx.coroutines.flow.*
import ru.investlifestyle.app.data.PostMapper
import ru.investlifestyle.app.data.mappers.toDomain
import ru.investlifestyle.app.data.networkApi.PostsApiInterface
import ru.investlifestyle.app.data.networkApi.PostsModelDataItem
import ru.investlifestyle.app.data.networkApi.examin.Repo
import ru.investlifestyle.app.data.paging.PostPagingRemoteMediator
import ru.investlifestyle.app.data.paging.PostPagingSource
import ru.investlifestyle.app.data.paging.PostTagsPagingSource
import ru.investlifestyle.app.data.room.LikePostsDaoRoom
import ru.investlifestyle.app.data.room.PostDaoRoom
import ru.investlifestyle.app.data.room.UserNameDaoRoom
import ru.investlifestyle.app.domain.models.PostModel
import ru.investlifestyle.app.domain.repository.PostRepositoryInterface
import ru.investlifestyle.app.ui.models.PostUiModel

@ExperimentalPagingApi
class PostsRepositoryImpl @Inject constructor(
    private val apiClient: PostsApiInterface,
    private val mapper: PostMapper,
    private val postDaoRoom: PostDaoRoom,
    private val likePostsDaoRoom: LikePostsDaoRoom,
    private val userNameDaoRoom: UserNameDaoRoom
) : PostRepositoryInterface {

    private val postPagingRemoteMediator = PostPagingRemoteMediator(postDaoRoom, apiClient, mapper)

    private val service = Repo()

    override fun getPostPagingRemoteMediator(): Flow<PagingData<PostUiModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                prefetchDistance = 1,
                enablePlaceholders = true
            ),
            remoteMediator = postPagingRemoteMediator,
            pagingSourceFactory = {
                postDaoRoom.getPostListPagingSource()
            }
        ).flow
            .map { pagingDate ->
                pagingDate.map { mapper.mapPostDbModelToPostUiModel(it) }
            }
    }

    override fun getPostPagingSource(categoryId: Int) = Pager(
        config = PagingConfig(
            pageSize = 10,
            maxSize = 30,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            PostPagingSource(
                categoryId = categoryId,
                apiClient = apiClient,
                mapper = mapper
            )
        }
    ).flow

    override fun getPostTagsPagingSource(tagsId: Int) = Pager(
        config = PagingConfig(
            pageSize = 10,
            maxSize = 30,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            PostTagsPagingSource(
                tagsId = tagsId,
                apiClient = apiClient,
                mapper = mapper
            )
        }
    ).flow

    override suspend fun getPostsList(postsCount: Int): List<PostsModelDataItem> {
        return service.getPost(1)
    }

    override suspend fun getMainPostList(page: Int): List<PostUiModel> {
        return mapper.mapListPostDataToListPostUi(apiClient.getPostsList(page))
    }

    override suspend fun loadOnePost(postId: Int): PostModel =
        apiClient.loadOnePostById(postId).toDomain()

    override suspend fun loadSubjectPosts(
        categories: Int,
        page: Int,
        perPage: Int,
        embed: Boolean
    ): List<PostUiModel> {
        return mapper.mapListPostDataToListPostUi(
            apiClient.loadSubjectPosts(categories, page, perPage, embed)
        )
    }

    override suspend fun loadSubjectPostsFlow(
        categories: Int,
        page: Int,
        perPage: Int,
        embed: Boolean
    ): List<PostUiModel> =
        mapper.mapListPostDataToListPostUi(
            apiClient.loadSubjectPosts(categories, page, perPage, embed)
        )

    override suspend fun loadSubjectTagsPosts(
        tags: Int,
        page: Int,
        perPage: Int,
        embed: Boolean
    ): List<PostUiModel> {
        return mapper.mapListPostDataToListPostUi(
            apiClient.loadSubjectTagsPosts(tags, page, perPage, embed)
        )
    }

    override suspend fun likePostIsEmpty(): Boolean = userNameDaoRoom.isEmpty()

    override suspend fun getLikePostById(postId: Int): PostUiModel {
        return mapper.mapLikePostDbModelToPostUiModel(likePostsDaoRoom.getLikePostById(postId))
    }

    override suspend fun getAllLikePosts(): List<PostUiModel> {
        return mapper.mapListLikePostBdModelToListPostUiModel(likePostsDaoRoom.getAllLikePosts())
    }

    override suspend fun insertLikePost(likePost: PostUiModel) {
        likePostsDaoRoom.insertLikePost(mapper.mapPostUiModelToLikePostDbModel(likePost))
    }

    override suspend fun deleteLikePostById(id: Int) {
        likePostsDaoRoom.deleteLikePostById(id)
    }

    override suspend fun getLikePostByIdBoolean(id: Int): Boolean {
        return likePostsDaoRoom.getLikePostByIdBoolean(id)
    }
}
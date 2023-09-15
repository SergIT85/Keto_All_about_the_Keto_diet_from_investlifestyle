package ru.investlifestyle.app.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.investlifestyle.app.data.networkApi.PostsModelDataItem
import ru.investlifestyle.app.domain.models.PostModel
import ru.investlifestyle.app.ui.models.PostUiModel

interface PostRepositoryInterface {

    fun getPostPagingRemoteMediator(): Flow<PagingData<PostUiModel>>
    fun getPostPagingSource(categoryId: Int): Flow<PagingData<PostUiModel>>
    fun getPostTagsPagingSource(tagsId: Int): Flow<PagingData<PostUiModel>>
    suspend fun getPostsList(postsCount: Int): List<PostsModelDataItem>
    suspend fun getMainPostList(page: Int): List<PostUiModel>
    suspend fun loadOnePost(postId: Int): PostModel
    suspend fun loadSubjectPosts(
        categories: Int,
        page: Int,
        perPage: Int,
        embed: Boolean
    ): List<PostUiModel>
    suspend fun loadSubjectPostsFlow(
        categories: Int,
        page: Int,
        perPage: Int,
        embed: Boolean
    ): List<PostUiModel>
    suspend fun loadSubjectTagsPosts(
        tags: Int,
        page: Int,
        perPage: Int,
        embed: Boolean
    ): List<PostUiModel>
    suspend fun likePostIsEmpty(): Boolean
    suspend fun getLikePostById(postId: Int): PostUiModel
    suspend fun getAllLikePosts(): List<PostUiModel>
    suspend fun insertLikePost(likePost: PostUiModel)
    suspend fun deleteLikePostById(id: Int)
    suspend fun getLikePostByIdBoolean(id: Int): Boolean
}
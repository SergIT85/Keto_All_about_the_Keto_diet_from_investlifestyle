package ru.investlifestyle.app.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.investlifestyle.app.data.models.categories.SaveCategories
import ru.investlifestyle.app.data.networkApi.PostsModelDataItem
import ru.investlifestyle.app.ui.models.PostUiModel
import ru.investlifestyle.app.ui.models.UserName

interface PostRepositoryInterface {

    suspend fun getUserName(): UserName
    suspend fun saveUserName(userName: UserName)
    suspend fun userNameIsEmpty(): Boolean
    fun getPostPagingRemoteMediator(): Flow<PagingData<PostUiModel>>
    fun getPostPagingSource(categoryId: Int): Flow<PagingData<PostUiModel>>
    fun getPostTagsPagingSource(tagsId: Int): Flow<PagingData<PostUiModel>>
    suspend fun getPostsList(postsCount: Int): List<PostsModelDataItem>
    suspend fun getMainPostList(page: Int): List<PostUiModel>
    suspend fun loadOnePost(postId: Int): PostUiModel
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
    fun getCategories(): Flow<List<SaveCategories>>
    fun getCategoriesForChoiceFragment(): Flow<List<SaveCategories>>
    fun getQuotes(): String
    suspend fun updateSubject(selected: Boolean, idCategory: Int)
    suspend fun getSingleSubjectById(idCategories: Int): SaveCategories
    suspend fun fillingDbInit()
    suspend fun likePostIsEmpty(): Boolean
    suspend fun getLikePostById(postId: Int): PostUiModel
    suspend fun getAllLikePosts(): List<PostUiModel>
    suspend fun insertLikePost(likePost: PostUiModel)
    suspend fun deleteLikePostById(id: Int)
    suspend fun getLikePostByIdBoolean(id: Int): Boolean
}
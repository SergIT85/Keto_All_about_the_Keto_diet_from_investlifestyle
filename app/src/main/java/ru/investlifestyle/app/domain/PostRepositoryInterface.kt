package ru.investlifestyle.app.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.investlifestyle.app.data.models.categories.SaveCategories
import ru.investlifestyle.app.data.networkApi.PostsModelDataItem
import ru.investlifestyle.app.ui.models.PostUiModel

interface PostRepositoryInterface {

    fun getPostPagingSource(): Flow<PagingData<PostUiModel>>
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
}
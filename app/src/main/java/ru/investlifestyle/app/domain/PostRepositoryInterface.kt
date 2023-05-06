package ru.investlifestyle.app.domain

import android.app.Application
import io.reactivex.Single
import ru.investlifestyle.app.data.networkApi.Categories
import ru.investlifestyle.app.data.networkApi.PostsModelDataItem
import ru.investlifestyle.app.ui.models.PostUiModel

interface PostRepositoryInterface {

    fun getPostsList(postsCount: Int): Single<List<PostsModelDataItem>>
    fun getMainPostList(postCount: Int): Single<List<PostUiModel>>
    suspend fun loadOnePost(postId: Int): PostUiModel
    fun loadSubjectPosts(
        categories: Int,
        page: Int,
        perPage: Int,
        embed: Boolean
    ): Single<List<PostsModelDataItem>>

    fun getCategories(): Single<List<Categories>>
    fun getQuotes(): String
}
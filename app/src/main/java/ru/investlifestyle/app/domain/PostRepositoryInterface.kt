package ru.investlifestyle.app.domain

import io.reactivex.Single
import ru.investlifestyle.app.data.networkApi.Categories
import ru.investlifestyle.app.data.networkApi.PostsModelDataItem
import ru.investlifestyle.app.ui.models.PostUiModel

interface PostRepositoryInterface {

    fun getPostsList(postsCount: Int): Single<List<PostsModelDataItem>>
    fun getMainPostList(postCount: Int): Single<List<PostUiModel>>
    fun loadOnePost(postId: Int): Single<PostsModelDataItem>
    fun loadSubjectPosts(
        categories: Int,
        page: Int,
        perPage: Int,
        embed: Boolean
    ): Single<List<PostsModelDataItem>>

    fun getCategories(): Single<List<Categories>>
}
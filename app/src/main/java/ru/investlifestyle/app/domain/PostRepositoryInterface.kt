package ru.investlifestyle.app.domain

import io.reactivex.Single
import ru.investlifestyle.app.utils.Categories
import ru.investlifestyle.app.utils.PostsModelDataItem

interface PostRepositoryInterface {

    fun getPostsList(postsCount: Int): Single<List<PostsModelDataItem>>
    fun loadOnePost(postId: Int): Single<PostsModelDataItem>
    fun loadSubjectPosts(
        categories: Int,
        page: Int,
        perPage: Int,
        embed: Boolean
    ): Single<List<PostsModelDataItem>>

    fun getCategories(): Single<List<Categories>>
}
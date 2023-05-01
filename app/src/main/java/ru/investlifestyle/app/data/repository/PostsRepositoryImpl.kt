package ru.investlifestyle.app.data.repository

import io.reactivex.Single
import ru.investlifestyle.app.data.networkApi.Categories
import ru.investlifestyle.app.data.networkApi.PostsApiInterface
import ru.investlifestyle.app.data.networkApi.PostsModelDataItem
import ru.investlifestyle.app.data.networkApi.examin.Repo
import ru.investlifestyle.app.domain.PostRepositoryInterface
import javax.inject.Inject

class PostsRepositoryImpl @Inject constructor(
    private val apiClient: PostsApiInterface
): PostRepositoryInterface {

    private val service = Repo()


    override fun getPostsList(postsCount: Int): Single<List<PostsModelDataItem>> {
        return service.getPost(1)
    }

    override fun loadOnePost(postId: Int): Single<PostsModelDataItem> {
        return apiClient.loadOnePostById(postId)
    }

    override fun loadSubjectPosts(
        categories: Int,
        page: Int,
        perPage: Int,
        embed: Boolean
    ): Single<List<PostsModelDataItem>> {
        return apiClient.loadSubjectPosts(categories, page, perPage, embed)
    }

    override fun getCategories(): Single<List<Categories>> {
        return apiClient.getCategories()
    }
}
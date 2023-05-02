package ru.investlifestyle.app.domain.usecase

import io.reactivex.Single
import ru.investlifestyle.app.data.networkApi.PostsModelDataItem
import ru.investlifestyle.app.domain.PostRepositoryInterface

class LoadSubjectPostsUseCase(
    private val postsRepository: PostRepositoryInterface
) {
    fun loadSubjectPosts(
        categories: Int,
        page: Int,
        perPage: Int,
        embed: Boolean
    ): Single<List<PostsModelDataItem>> {
        return postsRepository.loadSubjectPosts(categories, page, perPage, embed)
    }

}
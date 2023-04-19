package ru.investlifestyle.app.domain.usecase

import io.reactivex.Single
import ru.investlifestyle.app.domain.PostRepositoryInterface
import ru.investlifestyle.app.utils.PostsModelDataItem

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
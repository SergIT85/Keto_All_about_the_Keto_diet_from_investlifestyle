package ru.investlifestyle.app.domain.usecase

import javax.inject.Inject
import ru.investlifestyle.app.domain.PostRepositoryInterface
import ru.investlifestyle.app.ui.models.PostUiModel

class LoadSubjectPostsUseCase @Inject constructor(
    private val postRepository: PostRepositoryInterface
) {
    suspend fun loadSubjectPosts(
        categories: Int,
        page: Int,
        perPage: Int,
        embed: Boolean
    ): List<PostUiModel> {
        return postRepository.loadSubjectPosts(
            categories = categories,
            page = page,
            perPage = perPage,
            embed = embed
        )
    }
}
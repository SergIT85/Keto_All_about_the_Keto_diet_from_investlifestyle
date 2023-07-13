package ru.investlifestyle.app.domain.usecase

import javax.inject.Inject
import ru.investlifestyle.app.domain.PostRepositoryInterface
import ru.investlifestyle.app.ui.models.PostUiModel

class LoadSubjectPostsUseCase @Inject constructor(
    private val postRepository: PostRepositoryInterface
) {
    suspend fun loadSubjectPosts(
        categories: Int
    ): List<PostUiModel> {
        return postRepository.loadSubjectPosts(
            categories = categories,
            page = PAGE,
            perPage = PERPAGE,
            embed = EMBED
        )
    }
    companion object {
        const val PERPAGE = 10
        const val PAGE = 1
        const val EMBED = true
    }
}
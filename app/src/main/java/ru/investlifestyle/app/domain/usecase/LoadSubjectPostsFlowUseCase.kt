package ru.investlifestyle.app.domain.usecase

import javax.inject.Inject
import ru.investlifestyle.app.domain.repository.PostRepositoryInterface
import ru.investlifestyle.app.ui.models.PostUiModel

class LoadSubjectPostsFlowUseCase @Inject constructor(
    private val postRepository: PostRepositoryInterface
) {
    suspend fun loadSubjectPostsFlow(
        categories: Int
    ): List<PostUiModel> {
        return postRepository.loadSubjectPostsFlow(
            categories = categories,
            page = PAGE,
            perPage = PERPAGE,
            embed = EMBED
        )
    }
    companion object {
        const val PAGE = 1
        const val PERPAGE = 10
        const val EMBED = true
    }
}
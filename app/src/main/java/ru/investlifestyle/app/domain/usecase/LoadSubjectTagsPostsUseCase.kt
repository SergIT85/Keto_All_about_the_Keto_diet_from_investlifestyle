package ru.investlifestyle.app.domain.usecase

import javax.inject.Inject
import ru.investlifestyle.app.domain.repository.PostRepositoryInterface
import ru.investlifestyle.app.ui.models.PostUiModel

class LoadSubjectTagsPostsUseCase @Inject constructor(
    private val postRepository: PostRepositoryInterface
) {
    suspend fun loadSubjectTagsPosts(
        tags: Int
    ): List<PostUiModel> {
        return postRepository.loadSubjectTagsPosts(
            tags = tags,
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
package ru.investlifestyle.app.domain.usecase

import javax.inject.Inject
import ru.investlifestyle.app.domain.PostRepositoryInterface
import ru.investlifestyle.app.ui.models.PostUiModel

class LoadSubjectTagsPostsUseCase @Inject constructor(
    private val postRepository: PostRepositoryInterface
) {
    suspend fun loadSubjectTagsPosts(
        tags: Int,
        page: Int,
        perPage: Int,
        embed: Boolean
    ): List<PostUiModel> {
        return postRepository.loadSubjectTagsPosts(
            tags = tags,
            page = page,
            perPage = perPage,
            embed = embed
        )
    }
}
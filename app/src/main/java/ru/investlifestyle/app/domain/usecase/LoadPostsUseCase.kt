package ru.investlifestyle.app.domain.usecase

import javax.inject.Inject
import ru.investlifestyle.app.domain.repository.PostRepositoryInterface
import ru.investlifestyle.app.ui.models.PostUiModel

class LoadPostsUseCase @Inject constructor(
    private val postRepository: PostRepositoryInterface
) {
    suspend fun getMainPostList(page: Int): List<PostUiModel> {
        return postRepository.getMainPostList(page)
    }
}
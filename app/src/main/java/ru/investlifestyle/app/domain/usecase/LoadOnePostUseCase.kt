package ru.investlifestyle.app.domain.usecase

import javax.inject.Inject
import ru.investlifestyle.app.domain.PostRepositoryInterface
import ru.investlifestyle.app.ui.models.PostUiModel

class LoadOnePostUseCase @Inject constructor(
    private val postRepository: PostRepositoryInterface
) {
    suspend fun loadOnePost(postId: Int): PostUiModel {
        return postRepository.loadOnePost(postId)
    }
}
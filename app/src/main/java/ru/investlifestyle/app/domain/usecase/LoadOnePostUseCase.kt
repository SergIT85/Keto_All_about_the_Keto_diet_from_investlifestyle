package ru.investlifestyle.app.domain.usecase

import javax.inject.Inject
import ru.investlifestyle.app.domain.models.PostModel
import ru.investlifestyle.app.domain.repository.PostRepositoryInterface

class LoadOnePostUseCase @Inject constructor(
    private val postRepository: PostRepositoryInterface
) {
    suspend fun loadOnePost(postId: Int): PostModel {
        return postRepository.loadOnePost(postId)
    }
}
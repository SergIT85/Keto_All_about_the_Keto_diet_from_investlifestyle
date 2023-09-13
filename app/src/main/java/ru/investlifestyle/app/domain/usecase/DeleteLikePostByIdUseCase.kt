package ru.investlifestyle.app.domain.usecase

import javax.inject.Inject
import ru.investlifestyle.app.domain.repository.PostRepositoryInterface

class DeleteLikePostByIdUseCase @Inject constructor(
    private val postRepositoryInterface: PostRepositoryInterface
) {
    suspend fun deleteLikePostById(id: Int) {
        postRepositoryInterface.deleteLikePostById(id)
    }
}
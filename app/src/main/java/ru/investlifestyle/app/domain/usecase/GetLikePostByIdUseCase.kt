package ru.investlifestyle.app.domain.usecase

import javax.inject.Inject
import ru.investlifestyle.app.domain.PostRepositoryInterface
import ru.investlifestyle.app.ui.models.PostUiModel

class GetLikePostByIdUseCase @Inject constructor(
    private val postRepositoryInterface: PostRepositoryInterface
) {
    suspend fun getLikePostById(id: Int): PostUiModel {
        return postRepositoryInterface.getLikePostById(id)
    }
}
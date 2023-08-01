package ru.investlifestyle.app.domain.usecase

import javax.inject.Inject
import ru.investlifestyle.app.domain.PostRepositoryInterface
import ru.investlifestyle.app.ui.models.PostUiModel

class InsertLikePostUseCase @Inject constructor(
    private val postRepositoryInterface: PostRepositoryInterface
) {
    suspend fun insertLikePost(postUiModel: PostUiModel) {
        postRepositoryInterface.insertLikePost(postUiModel)
    }
}
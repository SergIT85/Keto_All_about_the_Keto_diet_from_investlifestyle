package ru.investlifestyle.app.domain.usecase

import javax.inject.Inject
import ru.investlifestyle.app.domain.repository.PostRepositoryInterface

class LikePostsIsEmptyUseCase @Inject constructor(
    private val postRepositoryInterface: PostRepositoryInterface
) {
    suspend fun likePostsIsEmpty(): Boolean = postRepositoryInterface.likePostIsEmpty()
}
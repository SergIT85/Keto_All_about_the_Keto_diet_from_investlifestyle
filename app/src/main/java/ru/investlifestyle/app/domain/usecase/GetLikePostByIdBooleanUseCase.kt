package ru.investlifestyle.app.domain.usecase

import javax.inject.Inject
import ru.investlifestyle.app.domain.PostRepositoryInterface

class GetLikePostByIdBooleanUseCase @Inject constructor(
    private val postRepositoryInterface: PostRepositoryInterface
) {
    suspend fun getLikePostByIdBoolean(id: Int): Boolean {
        return postRepositoryInterface.getLikePostByIdBoolean(id)
    }
}
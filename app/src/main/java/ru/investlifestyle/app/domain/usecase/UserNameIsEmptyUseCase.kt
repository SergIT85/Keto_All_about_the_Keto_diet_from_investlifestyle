package ru.investlifestyle.app.domain.usecase

import javax.inject.Inject
import ru.investlifestyle.app.domain.PostRepositoryInterface

class UserNameIsEmptyUseCase @Inject constructor(
    private val postRepositoryInterface: PostRepositoryInterface
) {
    suspend fun userNameIsEmpty() = postRepositoryInterface.userNameIsEmpty()
}
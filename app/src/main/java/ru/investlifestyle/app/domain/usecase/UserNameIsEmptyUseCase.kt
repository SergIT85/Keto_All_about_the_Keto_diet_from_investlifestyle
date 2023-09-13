package ru.investlifestyle.app.domain.usecase

import javax.inject.Inject
import ru.investlifestyle.app.domain.repository.UserNameRepositoryInterface

class UserNameIsEmptyUseCase @Inject constructor(
    private val userNameRepositoryInterface: UserNameRepositoryInterface
) {
    suspend fun userNameIsEmpty() = userNameRepositoryInterface.userNameIsEmpty()
}
package ru.investlifestyle.app.domain.usecase

import javax.inject.Inject
import ru.investlifestyle.app.domain.models.UserName
import ru.investlifestyle.app.domain.repository.UserNameRepositoryInterface

class SaveUserNameUseCase @Inject constructor(
    private val userNameRepositoryInterface: UserNameRepositoryInterface
) {
    suspend fun saveUserName(userName: UserName) {
        userNameRepositoryInterface.saveUserName(userName)
    }
}
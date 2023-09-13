package ru.investlifestyle.app.domain.repository

import ru.investlifestyle.app.domain.models.UserName

interface UserNameRepositoryInterface {
    suspend fun getUserName(): UserName
    suspend fun saveUserName(userName: UserName)
    suspend fun userNameIsEmpty(): Boolean
}
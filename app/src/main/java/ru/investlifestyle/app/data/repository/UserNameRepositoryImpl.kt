package ru.investlifestyle.app.data.repository

import javax.inject.Inject
import ru.investlifestyle.app.data.mappers.toDomain
import ru.investlifestyle.app.data.mappers.toDomainEntity
import ru.investlifestyle.app.data.room.UserNameDaoRoom
import ru.investlifestyle.app.domain.models.UserName
import ru.investlifestyle.app.domain.repository.UserNameRepositoryInterface

class UserNameRepositoryImpl @Inject constructor(
    private val userNameDaoRoom: UserNameDaoRoom

) : UserNameRepositoryInterface {

    override suspend fun getUserName(): UserName {
        return userNameDaoRoom.getUserName().toDomain()
    }

    override suspend fun saveUserName(userName: UserName) {
        userNameDaoRoom.save(userName.toDomainEntity())
    }

    override suspend fun userNameIsEmpty(): Boolean {
        return userNameDaoRoom.isEmpty()
    }
}
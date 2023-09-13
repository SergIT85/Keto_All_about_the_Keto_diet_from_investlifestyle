package ru.investlifestyle.app.data.mappers

import ru.investlifestyle.app.data.room.UserNameEntity
import ru.investlifestyle.app.domain.models.UserName

fun UserName.toDomainEntity(): UserNameEntity {
    return UserNameEntity(1, this.userName)
}

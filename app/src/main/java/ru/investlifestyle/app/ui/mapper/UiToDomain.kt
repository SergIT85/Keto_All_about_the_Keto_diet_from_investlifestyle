package ru.investlifestyle.app.ui.mapper

import ru.investlifestyle.app.domain.models.UserName
import ru.investlifestyle.app.ui.models.UserNameUi

fun UserNameUi.toDomain(): UserName =
    UserName(userName)
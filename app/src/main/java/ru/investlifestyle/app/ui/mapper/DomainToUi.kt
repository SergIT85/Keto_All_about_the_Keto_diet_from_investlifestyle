package ru.investlifestyle.app.ui.mapper

import ru.investlifestyle.app.domain.models.UserName
import ru.investlifestyle.app.ui.models.UserNameUi

fun UserName.toUi(): UserNameUi =
    UserNameUi(userName)
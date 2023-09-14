package ru.investlifestyle.app.ui.mapper

import ru.investlifestyle.app.domain.models.SaveCategories
import ru.investlifestyle.app.domain.models.UserName
import ru.investlifestyle.app.ui.models.SaveCategoriesUi
import ru.investlifestyle.app.ui.models.UserNameUi

fun UserName.toUi(): UserNameUi =
    UserNameUi(userName)

fun SaveCategories.toUi(): SaveCategoriesUi {
    return SaveCategoriesUi(
        nameCategory = this.nameCategory,
        typeCategory = this.typeCategory,
        idCategory = this.idCategory,
        selected = this.selected
    )
}
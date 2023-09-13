package ru.investlifestyle.app.ui.models

data class SaveCategoriesUi(
    val nameCategory: String,
    val typeCategory: String,
    val idCategory: Int,
    val selected: Boolean = true
)

package ru.investlifestyle.app.data.models.categories

data class SaveCategoriesData(
    val nameCategory: String,
    val typeCategory: String,
    val idCategory: Int,
    val selected: Boolean = true
)

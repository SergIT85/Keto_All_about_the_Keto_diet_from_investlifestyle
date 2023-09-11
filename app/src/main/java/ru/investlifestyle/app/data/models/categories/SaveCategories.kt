package ru.investlifestyle.app.data.models.categories

data class SaveCategories(
    val nameCategory: String,
    val typeCategory: String,
    val idCategory: Int,
    val selected: Boolean = true
)

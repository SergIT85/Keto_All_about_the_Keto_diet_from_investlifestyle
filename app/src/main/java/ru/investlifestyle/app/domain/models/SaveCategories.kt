package ru.investlifestyle.app.domain.models

data class SaveCategories(
    val nameCategory: String,
    val typeCategory: String,
    val idCategory: Int,
    val selected: Boolean = true
)

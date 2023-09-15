package ru.investlifestyle.app.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.investlifestyle.app.domain.models.SaveCategories

interface CategoriesRepositoryInterface {
    fun getCategories(): Flow<List<SaveCategories>>
    fun getQuotes(): String
    suspend fun updateSubject(selected: Boolean, idCategory: Int)
    suspend fun fillingDbInit()
}
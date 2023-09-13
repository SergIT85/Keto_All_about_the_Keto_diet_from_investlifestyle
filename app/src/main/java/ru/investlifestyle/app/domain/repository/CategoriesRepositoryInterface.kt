package ru.investlifestyle.app.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.investlifestyle.app.domain.models.SaveCategories

interface CategoriesRepositoryInterface {
    fun getCategories(): Flow<List<SaveCategories>>
    fun getCategoriesForChoiceFragment(): Flow<List<SaveCategories>>
    fun getQuotes(): String
    suspend fun updateSubject(selected: Boolean, idCategory: Int)
    suspend fun getSingleSubjectById(idCategories: Int): SaveCategories
    suspend fun fillingDbInit()
}
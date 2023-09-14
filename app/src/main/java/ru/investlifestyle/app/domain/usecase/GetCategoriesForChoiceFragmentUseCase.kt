package ru.investlifestyle.app.domain.usecase

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import ru.investlifestyle.app.domain.models.SaveCategories
import ru.investlifestyle.app.domain.repository.CategoriesRepositoryInterface

class GetCategoriesForChoiceFragmentUseCase @Inject constructor(
    private val categoryRepository: CategoriesRepositoryInterface
) {
    suspend fun getCategories(): Flow<List<SaveCategories>> {
        return categoryRepository.getCategories()
    }
}
package ru.investlifestyle.app.domain.usecase

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import ru.investlifestyle.app.data.models.categories.SaveCategoriesData
import ru.investlifestyle.app.domain.repository.PostRepositoryInterface

class GetCategoriesUseCase @Inject constructor(
    private val postRepository: PostRepositoryInterface
) {
    suspend fun getCategories(): Flow<List<SaveCategoriesData>> {
        return postRepository.getCategories()
    }
}
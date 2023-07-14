package ru.investlifestyle.app.domain.usecase

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import ru.investlifestyle.app.data.models.categories.SaveCategories
import ru.investlifestyle.app.domain.PostRepositoryInterface

class GetCategoriesForChoiceFragmentUseCase @Inject constructor(
    private val postRepository: PostRepositoryInterface
) {
    suspend fun getCategories(): Flow<List<SaveCategories>> {
        return postRepository.getCategories()
    }
}
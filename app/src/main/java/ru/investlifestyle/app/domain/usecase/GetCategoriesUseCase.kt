package ru.investlifestyle.app.domain.usecase

import ru.investlifestyle.app.data.models.categories.SaveCategories
import ru.investlifestyle.app.domain.PostRepositoryInterface
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val postRepository: PostRepositoryInterface
) {
    suspend fun getCategories(): List<SaveCategories> {
        return postRepository.getCategories()
    }
}
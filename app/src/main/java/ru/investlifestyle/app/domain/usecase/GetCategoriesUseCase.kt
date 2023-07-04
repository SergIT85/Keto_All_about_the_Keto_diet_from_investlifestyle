package ru.investlifestyle.app.domain.usecase

import javax.inject.Inject
import ru.investlifestyle.app.data.models.categories.SaveCategories
import ru.investlifestyle.app.domain.PostRepositoryInterface

class GetCategoriesUseCase @Inject constructor(
    private val postRepository: PostRepositoryInterface
) {
    suspend fun getCategories(): List<SaveCategories> {
        return postRepository.getCategories()
    }
}
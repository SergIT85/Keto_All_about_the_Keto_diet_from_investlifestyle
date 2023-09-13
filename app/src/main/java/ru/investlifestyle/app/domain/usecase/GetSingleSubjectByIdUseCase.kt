package ru.investlifestyle.app.domain.usecase

import javax.inject.Inject
import ru.investlifestyle.app.data.models.categories.SaveCategoriesData
import ru.investlifestyle.app.domain.repository.PostRepositoryInterface

class GetSingleSubjectByIdUseCase @Inject constructor(
    private val postRepositoryInterface: PostRepositoryInterface
) {
    suspend fun getSingleSubjectById(id: Int): SaveCategoriesData {
        return postRepositoryInterface.getSingleSubjectById(id)
    }
}
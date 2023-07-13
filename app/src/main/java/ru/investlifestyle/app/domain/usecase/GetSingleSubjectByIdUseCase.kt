package ru.investlifestyle.app.domain.usecase

import javax.inject.Inject
import ru.investlifestyle.app.data.models.categories.SaveCategories
import ru.investlifestyle.app.domain.PostRepositoryInterface

class GetSingleSubjectByIdUseCase @Inject constructor(
    private val postRepositoryInterface: PostRepositoryInterface
) {
    suspend fun getSingleSubjectById(id: Int): SaveCategories {
        return postRepositoryInterface.getSingleSubjectById(id)
    }
}
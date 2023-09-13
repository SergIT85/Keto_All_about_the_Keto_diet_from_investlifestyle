package ru.investlifestyle.app.domain.usecase

import javax.inject.Inject
import ru.investlifestyle.app.domain.repository.PostRepositoryInterface

class UpdateSubjectUseCase @Inject constructor(
    private val postRepositoryInterface: PostRepositoryInterface
) {
    suspend fun updateSubject(selected: Boolean, idCategory: Int) {
        postRepositoryInterface.updateSubject(selected = selected, idCategory = idCategory)
    }
}
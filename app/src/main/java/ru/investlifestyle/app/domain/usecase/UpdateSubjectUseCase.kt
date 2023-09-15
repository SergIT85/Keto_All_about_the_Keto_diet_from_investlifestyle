package ru.investlifestyle.app.domain.usecase

import javax.inject.Inject
import ru.investlifestyle.app.domain.repository.CategoriesRepositoryInterface

class UpdateSubjectUseCase @Inject constructor(
    private val repositoryInterface: CategoriesRepositoryInterface
) {
    suspend fun updateSubject(selected: Boolean, idCategory: Int) {
        repositoryInterface.updateSubject(selected = selected, idCategory = idCategory)
    }
}
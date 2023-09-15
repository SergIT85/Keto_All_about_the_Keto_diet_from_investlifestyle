package ru.investlifestyle.app.domain.usecase

import javax.inject.Inject
import ru.investlifestyle.app.domain.repository.CategoriesRepositoryInterface

class FillingDbInitUseCase @Inject constructor(
    private val repositoryInterface: CategoriesRepositoryInterface
) {
    suspend fun fillingDbInit() {
        repositoryInterface.fillingDbInit()
    }
}
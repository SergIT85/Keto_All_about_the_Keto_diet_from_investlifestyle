package ru.investlifestyle.app.domain.usecase

import javax.inject.Inject
import ru.investlifestyle.app.domain.repository.CategoriesRepositoryInterface

class GetQuotesUseCase @Inject constructor(
    private val repository: CategoriesRepositoryInterface
) {
    fun getQuotes(): String {
        return repository.getQuotes()
    }
}
package ru.investlifestyle.app.domain.usecase

import javax.inject.Inject
import ru.investlifestyle.app.domain.PostRepositoryInterface

class GetQuotesUseCase @Inject constructor(
    private val postRepository: PostRepositoryInterface
) {
    fun getQuotes(): String {
        return postRepository.getQuotes()
    }
}
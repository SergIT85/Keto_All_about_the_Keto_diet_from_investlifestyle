package ru.investlifestyle.app.domain.usecase

import android.app.Application
import ru.investlifestyle.app.domain.PostRepositoryInterface
import javax.inject.Inject

class GetQuotesUseCase @Inject constructor(
    private val postRepository: PostRepositoryInterface
) {
    fun getQuotes() : String {
        return postRepository.getQuotes()
    }
}
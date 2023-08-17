package ru.investlifestyle.app.domain.usecase

import android.annotation.SuppressLint
import androidx.paging.PagingData
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import ru.investlifestyle.app.domain.PostRepositoryInterface
import ru.investlifestyle.app.ui.models.PostUiModel

class GetPostPagingSourceUseCase @Inject constructor(
    private val postRepositoryInterface: PostRepositoryInterface
) {
    @SuppressLint("LogNotTimber")
    fun getPostPagingSource(categoryId: Int, categoryType: String): Flow<PagingData<PostUiModel>> {
        return if (categoryType == CATEGORIES) {
            postRepositoryInterface.getPostPagingSource(categoryId)
        } else {
            postRepositoryInterface.getPostTagsPagingSource(categoryId)
        }
        // Отработать вариант с открытием из базы данных сохраеннных постов
    }
    companion object {
        const val CATEGORIES = "categories"
        const val TAGS = "tags"
    }
}
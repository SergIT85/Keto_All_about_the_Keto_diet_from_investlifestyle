package ru.investlifestyle.app.domain.usecase

import androidx.paging.PagingData
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import ru.investlifestyle.app.domain.PostRepositoryInterface
import ru.investlifestyle.app.ui.models.PostUiModel

class GetPostPagingSourceUseCase @Inject constructor(
    private val postRepositoryInterface: PostRepositoryInterface
) {
    fun getPostPagingSource(categoryId: Int): Flow<PagingData<PostUiModel>> {
        return postRepositoryInterface.getPostPagingSource(categoryId)
    }
}
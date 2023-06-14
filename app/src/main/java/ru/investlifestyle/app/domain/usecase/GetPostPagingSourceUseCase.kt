package ru.investlifestyle.app.domain.usecase

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.investlifestyle.app.domain.PostRepositoryInterface
import ru.investlifestyle.app.ui.models.PostUiModel
import javax.inject.Inject

class GetPostPagingSourceUseCase @Inject constructor(
    private val postRepository: PostRepositoryInterface
) {

    fun getPostPagingSource(): Flow<PagingData<PostUiModel>> {
        return postRepository.getPostPagingSource()
    }
}
package ru.investlifestyle.app.domain.usecase

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import ru.investlifestyle.app.domain.PostRepositoryInterface
import ru.investlifestyle.app.ui.models.PostUiModel
import javax.inject.Inject

class GetPostPagingSourceUseCase @Inject constructor(
    private val postRepository: PostRepositoryInterface
) {

    fun getPostPagingSource(): LiveData<PagingData<PostUiModel>> {
        return postRepository.getPostPagingSource()
    }
}
package ru.investlifestyle.app.domain.usecase

import androidx.paging.PagingData
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import ru.investlifestyle.app.domain.PostRepositoryInterface
import ru.investlifestyle.app.ui.models.PostUiModel

class GetPostPagingRemoteMediatorUseCase @Inject constructor(
    private val postRepository: PostRepositoryInterface
) {

    fun getPostPagingRemoteMediator(): Flow<PagingData<PostUiModel>> {
        return postRepository.getPostPagingRemoteMediator()
    }
}
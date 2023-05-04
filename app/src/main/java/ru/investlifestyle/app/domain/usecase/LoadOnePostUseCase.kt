package ru.investlifestyle.app.domain.usecase


import io.reactivex.Single
import kotlinx.coroutines.flow.Flow
import ru.investlifestyle.app.data.networkApi.PostsModelDataItem
import ru.investlifestyle.app.domain.PostRepositoryInterface
import ru.investlifestyle.app.ui.models.PostUiModel
import javax.inject.Inject

class LoadOnePostUseCase @Inject constructor (
    private val postRepository: PostRepositoryInterface
    ) {
    suspend fun loadOnePost(postId: Int): PostUiModel {
        return postRepository.loadOnePost(postId)
    }
}
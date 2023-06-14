package ru.investlifestyle.app.domain.usecase

import io.reactivex.Single
import ru.investlifestyle.app.domain.PostRepositoryInterface
import ru.investlifestyle.app.ui.models.PostUiModel
import javax.inject.Inject

class LoadPostsUseCase @Inject constructor(
    private val postRepository: PostRepositoryInterface
) {
    suspend fun getMainPostList(page: Int): List<PostUiModel> {
        return postRepository.getMainPostList(page)
    }
}
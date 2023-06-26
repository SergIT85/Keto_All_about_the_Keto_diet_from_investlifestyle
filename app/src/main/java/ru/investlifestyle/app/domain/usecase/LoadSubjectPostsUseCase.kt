package ru.investlifestyle.app.domain.usecase

import io.reactivex.Single
import ru.investlifestyle.app.data.networkApi.PostsModelDataItem
import ru.investlifestyle.app.domain.PostRepositoryInterface
import ru.investlifestyle.app.ui.models.PostUiModel
import javax.inject.Inject

class LoadSubjectPostsUseCase @Inject constructor(
    private val postRepository: PostRepositoryInterface
) {
    suspend fun loadSubjectPosts(
        categories: Int,
        page: Int,
        per_page: Int,
        _embed: Boolean
    ): List<PostUiModel> {
        return postRepository.loadSubjectPosts(
            categories = categories,
            page = page,
            per_page = per_page,
            _embed = _embed
        )
    }

}
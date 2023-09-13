package ru.investlifestyle.app.domain.usecase

import javax.inject.Inject
import ru.investlifestyle.app.data.networkApi.PostsModelDataItem
import ru.investlifestyle.app.domain.repository.PostRepositoryInterface

class GetMainPostsListUseCase @Inject constructor(
    private val postRepository: PostRepositoryInterface
) {

    // Необходимо доделать для получения списка всех постов по порядку
    suspend fun getMainPostsList(postsCount: Int): List<PostsModelDataItem> {
        return postRepository.getPostsList(postsCount)
    }
}
package ru.investlifestyle.app.domain.usecase

import io.reactivex.Single
import ru.investlifestyle.app.data.networkApi.PostsModelDataItem
import ru.investlifestyle.app.domain.PostRepositoryInterface
import javax.inject.Inject

class GetMainPostsListUseCase @Inject constructor(
    private val postRepository: PostRepositoryInterface
) {

    //Необходимо доделать для получения списка всех постов по порядку
    fun getMainPostsList(postsCount: Int): Single<List<PostsModelDataItem>> {
        return postRepository.getPostsList(postsCount)
    }
}
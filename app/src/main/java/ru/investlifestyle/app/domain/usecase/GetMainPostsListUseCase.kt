package ru.investlifestyle.app.domain.usecase

import io.reactivex.Single
import ru.investlifestyle.app.domain.PostRepositoryInterface
import ru.investlifestyle.app.utils.PostsModelDataItem
import javax.inject.Inject

class GetMainPostsListUseCase @Inject constructor(
    private val postRepository: PostRepositoryInterface
) {

    //Необходимо доделать для получения списка всех постов по порядку
    fun getMainPostsList(postsCount: Int): Single<List<PostsModelDataItem>> {
        return postRepository.getPostsList(postsCount)
    }
}
package ru.investlifestyle.app.domain.usecase


import io.reactivex.Single
import ru.investlifestyle.app.data.networkApi.PostsModelDataItem
import ru.investlifestyle.app.domain.PostRepositoryInterface

class LoadOnePostUseCase (
    private val postRepository: PostRepositoryInterface
    ) {
    fun loadOnePost(postId: Int): Single<PostsModelDataItem> {
        return postRepository.loadOnePost(postId)
    }
}
package ru.investlifestyle.app.data.repository

import io.reactivex.Single
import ru.investlifestyle.app.data.dto.ContentApi
import ru.investlifestyle.app.data.dto.PostApi
import ru.investlifestyle.app.data.dto.PostObjectAPi
import ru.investlifestyle.app.data.networkApi.PostsApiInterface
import ru.investlifestyle.app.utils.PostsModelData
import javax.inject.Inject

class PostsRepository @Inject constructor(
    private val service: PostsApiInterface
) {
    fun getPostsList(postsCount: Int): Single<PostsModelData> {
        return service.getPostsList(/*postsCount = postsCount*/)

    }
}
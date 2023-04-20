package ru.investlifestyle.app.data.networkApi.examin

import io.reactivex.Single
import ru.investlifestyle.app.utils.PostsModelDataItem
import javax.inject.Inject

class Repo @Inject constructor() {

    fun getPost(postsCount: Int): Single<List<PostsModelDataItem>> {
        return ApiClient.apiClient.getPostsList(/*postsCount = postsCount*/)
    }
}
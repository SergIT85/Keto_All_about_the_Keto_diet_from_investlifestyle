package ru.investlifestyle.app.data.networkApi.examin

import io.reactivex.Single
import ru.investlifestyle.app.data.networkApi.PostsModelDataItem
import javax.inject.Inject

class Repo @Inject constructor() {

    suspend fun getPost(postsCount: Int): List<PostsModelDataItem> {
        return ApiClient.apiClient.getPostsList(1)
    }
}
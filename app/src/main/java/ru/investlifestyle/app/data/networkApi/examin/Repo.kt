package ru.investlifestyle.app.data.networkApi.examin

import javax.inject.Inject
import ru.investlifestyle.app.data.networkApi.PostsModelDataItem

class Repo @Inject constructor() {

    suspend fun getPost(postsCount: Int): List<PostsModelDataItem> {
        return ApiClient.apiClient.getPostsList(1)
    }
}
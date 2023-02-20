package ru.investlifestyle.app.data.networkApi.examin

import io.reactivex.Single
import ru.investlifestyle.app.data.dto.ListPosts
import ru.investlifestyle.app.data.dto.PostApi
import ru.investlifestyle.app.data.dto.PostObjectAPi
import ru.investlifestyle.app.data.networkApi.PostsApiInterface
import ru.investlifestyle.app.utils.PostsModelData
import ru.investlifestyle.app.utils.PostsModelDataItem

class Repo {
    fun getPost(postsCount: Int): Single<List<PostsModelDataItem>> {
        return ApiClient.apiClient.getPostsList(/*postsCount = postsCount*/)
    }
}
package ru.investlifestyle.app.data.repository

import io.reactivex.Single
import ru.investlifestyle.app.data.dto.ContentApi
import ru.investlifestyle.app.data.dto.PostApi
import ru.investlifestyle.app.data.dto.PostObjectAPi
import ru.investlifestyle.app.data.networkApi.PostsApiInterface
import ru.investlifestyle.app.data.networkApi.examin.Repo
import ru.investlifestyle.app.domain.PostRepositoryInterface
import ru.investlifestyle.app.utils.PostsModelData
import ru.investlifestyle.app.utils.PostsModelDataItem
import javax.inject.Inject

class PostsRepositoryImpl @Inject constructor(): PostRepositoryInterface {

    private val service = Repo()

    override fun getPostsList(postsCount: Int): Single<List<PostsModelDataItem>> {
        return service.getPost(1)
    }
}
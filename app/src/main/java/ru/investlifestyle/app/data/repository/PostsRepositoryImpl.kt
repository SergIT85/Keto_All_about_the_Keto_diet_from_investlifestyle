package ru.investlifestyle.app.data.repository

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import io.reactivex.Single
import ru.investlifestyle.app.R
import ru.investlifestyle.app.data.PostMapper
import ru.investlifestyle.app.data.networkApi.Categories
import ru.investlifestyle.app.data.networkApi.PostsApiInterface
import ru.investlifestyle.app.data.networkApi.PostsModelDataItem
import ru.investlifestyle.app.data.networkApi.examin.Repo
import ru.investlifestyle.app.data.paging.PostPagingSource
import ru.investlifestyle.app.domain.PostRepositoryInterface
import ru.investlifestyle.app.ui.models.PostUiModel
import javax.inject.Inject
import kotlin.random.Random

class PostsRepositoryImpl @Inject constructor(
    private val apiClient: PostsApiInterface,
    private val mapper: PostMapper,
    private val application: Application
): PostRepositoryInterface {

    private val service = Repo()
    override fun getPostPagingSource(): LiveData<PagingData<PostUiModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                maxSize = 30,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                PostPagingSource(
                    apiClient,
                    mapper
                )
            }
        ).liveData
    }


    override suspend fun getPostsList(postsCount: Int): List<PostsModelDataItem> {
        return service.getPost(1)
    }

    override suspend fun getMainPostList(page: Int): List<PostUiModel> {
        return mapper.mapListPostDataToListPostUi(apiClient.getPostsList(page))
    }

    //исправить на загрузку из БД!!!! когда будет создана
    override suspend fun loadOnePost(postId: Int): PostUiModel =
        mapper.mapPostModelDataToPostUiModel(apiClient.loadOnePostById(postId))


    override fun loadSubjectPosts(
        categories: Int,
        page: Int,
        perPage: Int,
        embed: Boolean
    ): Single<List<PostsModelDataItem>> {
        return apiClient.loadSubjectPosts(categories, page, perPage, embed)
    }

    override fun getCategories(): Single<List<Categories>> {
        return apiClient.getCategories()
    }

    @SuppressLint("LogNotTimber")
    override fun getQuotes(): String {
        val randomString = Random(System.currentTimeMillis())
        val array = application.resources.getStringArray(R.array.quotes)
        return array[randomString.nextInt(array.size)]
    }
}
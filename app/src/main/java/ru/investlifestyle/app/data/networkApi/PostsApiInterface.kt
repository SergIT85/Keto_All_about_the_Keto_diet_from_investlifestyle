package ru.investlifestyle.app.data.networkApi

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import ru.investlifestyle.app.data.dto.PostApi
import ru.investlifestyle.app.data.dto.PostObjectAPi
import ru.investlifestyle.app.utils.PostsModelData
import ru.investlifestyle.app.utils.PostsModelDataItem

interface PostsApiInterface {

    @GET("wp-json/wp/v2/posts?per_page=1")
    fun getPostsList(/*@Path("posts_count") postsCount: Int*/): Single<List<PostsModelDataItem>>

}

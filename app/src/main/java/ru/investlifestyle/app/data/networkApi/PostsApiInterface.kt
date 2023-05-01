package ru.investlifestyle.app.data.networkApi

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PostsApiInterface {

    //общий запрос 1 страницы постов
    @GET("wp-json/wp/v2/posts?per_page=10")
    fun getPostsList(/*@Path("posts_count") postsCount: Int*/): Single<List<PostsModelDataItem>>

    @GET("wp-json/wp/v2/posts/{postId}?&_embed=true")
    fun loadOnePostById(
        @Path("postId") postId: Int
    ): Single<PostsModelDataItem>

    @GET("wp-json/wp/v2/posts")
    fun loadSubjectPosts(
        @Query("categories") categories:Int,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("_embed") embed: Boolean
    ): Single<List<PostsModelDataItem>>

    @GET("wp-json/wp/v2/categories?per_page=50")
    fun getCategories(): Single<List<Categories>>

}

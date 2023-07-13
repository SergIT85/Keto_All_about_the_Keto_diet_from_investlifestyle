package ru.investlifestyle.app.data.networkApi.examin

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.investlifestyle.app.data.networkApi.PostsApiInterface

object ApiClient {
    private const val BASE_URL = "https://investlifestyle.ru/"

    private var client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT).apply {
                this.level = HttpLoggingInterceptor.Level.BODY
            }
        )
        .build()

    val apiClient: PostsApiInterface by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        return@lazy retrofit.create(PostsApiInterface::class.java)
    }
}
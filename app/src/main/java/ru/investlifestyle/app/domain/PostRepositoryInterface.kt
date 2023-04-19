package ru.investlifestyle.app.domain

import io.reactivex.Single
import ru.investlifestyle.app.utils.PostsModelDataItem

interface PostRepositoryInterface {

    fun getPostsList(postsCount: Int): Single<List<PostsModelDataItem>>
}
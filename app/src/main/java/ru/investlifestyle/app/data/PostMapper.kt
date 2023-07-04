package ru.investlifestyle.app.data

import javax.inject.Inject
import ru.investlifestyle.app.data.networkApi.PostsModelDataItem
import ru.investlifestyle.app.data.room.PostDbModelEntity
import ru.investlifestyle.app.ui.models.PostUiModel

class PostMapper @Inject constructor() {

    fun mapPostModelDataToDbModelEntity(postModelData: PostsModelDataItem) = PostDbModelEntity(
        id = postModelData.id,
        link = postModelData.link,
        title = postModelData.title.rendered,
        posterMediaLinkUrl = getImageUrlFromYoastHead(postModelData.yoast_head),
        content = postModelData.content.rendered,
        protected = postModelData.content.protected,
        author = "postModelData.author.",
        categories = postModelData.categories.toString(),
        modifiedGmt = postModelData.modified_gmt
    )

    fun mapPostModelDataToPostUiModel(postModelData: PostsModelDataItem) = PostUiModel(
        id = postModelData.id,
        link = postModelData.link,
        title = postModelData.title.rendered,
        posterMediaLinkUrl = getImageUrlFromYoastHead(postModelData.yoast_head),
        content = postModelData.content.rendered,
        protected = postModelData.content.protected,
        author = "postModelData.author.",
        categories = postModelData.categories,
        modifiedGmt = postModelData.modified_gmt
    )

    fun mapPostDbModelToPostUiModel(postModelData: PostDbModelEntity) = PostUiModel(
        id = postModelData.id,
        link = postModelData.link,
        title = postModelData.title,
        posterMediaLinkUrl = postModelData.posterMediaLinkUrl,
        content = postModelData.content,
        protected = postModelData.protected,
        author = "postModelData.author.",
        categories = listOf(1, 2, 3), // ----------------------ПЕРЕДЕЛАТЬ НА MAP в LIST!!!!!
        modifiedGmt = postModelData.modifiedGmt
    )

    fun listPostModelToListDbModel(listPosts: List<PostsModelDataItem>) = listPosts.map {
        mapPostModelDataToDbModelEntity(it)
    }

    fun mapListPostDataToListPostUi(listPosts: List<PostsModelDataItem>) = listPosts.map {
        mapPostModelDataToPostUiModel(it)
    }

    private fun getImageUrlFromYoastHead(string: String): String {
        val listUrls = performRegex(string)
        var imageUrl = string
        listUrls.map {
            if (it.contains(IMAGER_URL_REGEX, ignoreCase = true)) {
                imageUrl = it
            }
        }
        return imageUrl
    }

    private fun performRegex(text: String): List<String> {
        val regPattern = Regex(REGEX_URL)
        val list = regPattern.findAll(text.toString()).map { it.value }.toList()
        return list
    }
    companion object {
        const val REGEX_URL = """(?:https?|ftp)://\S+"""
        const val IMAGER_URL_REGEX = "wp-content"
    }
}
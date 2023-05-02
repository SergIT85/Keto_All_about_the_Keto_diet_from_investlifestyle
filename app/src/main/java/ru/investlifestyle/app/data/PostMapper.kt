package ru.investlifestyle.app.data

import ru.investlifestyle.app.data.networkApi.PostsModelDataItem
import ru.investlifestyle.app.ui.models.PostUiModel
import javax.inject.Inject

class PostMapper @Inject constructor() {

    fun mapPostModelDataToPostUiModel(postModelData: PostsModelDataItem) = PostUiModel(
        id = postModelData.id,
        link = postModelData.link,
        title = postModelData.title.rendered,
        posterMediaLinkUrl = getImageUrlFromYoastHead(postModelData.yoast_head),
        content = postModelData.content.rendered,
        protected = postModelData.content.protected,
        author = "postModelData.author.",
        categories = postModelData.categories,
    )

    fun mapListPostDataToListPostUi(listPosts:List<PostsModelDataItem>) = listPosts.map {
        mapPostModelDataToPostUiModel(it)
    }

    fun getImageUrlFromYoastHead(string: String): String {
        val listUrls = performRegex(string)
        var imageUrl = ""
        listUrls.map {
            if (it.contains(IMAGER_URL_REGEX, ignoreCase = true)) {
                imageUrl = it
            }
        }
        return imageUrl
    }

    fun performRegex(text: String): List<String> {
        val regPattern = Regex(REGEX_URL)
        val list = regPattern.findAll(text.toString()).map { it.value }.toList()
        return list
    }
    companion object {
        const val REGEX_URL = /*"""((http|ftp|https):\/\/([\w_-]+(?:(?:\.[\w_-]+)+))([\w.,@?^=%&:\/~+#-]*[\w@?^=%&\/~+#-])?)"""*/ """(?:https?|ftp)://\S+"""
        const val IMAGER_URL_REGEX = "wp-content"
    }
}
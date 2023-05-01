package ru.investlifestyle.app.data

import ru.investlifestyle.app.data.networkApi.PostsModelDataItem
import ru.investlifestyle.app.ui.models.PostUiModel
import javax.inject.Inject

class PostMapper @Inject constructor() {

    fun mapPostModelDataToPostUiModel(postModelData: PostsModelDataItem) = PostUiModel(
        id = postModelData.id,
        link = postModelData.link,
        title = postModelData.title.rendered,
        posterMediaLinkUrl = "https://investlifestyle.ru/wp-content/uploads/2023/04/Resize-mo6i8OQZl5TAO.jpg",
        content = postModelData.content.rendered,
        protected = postModelData.content.protected,
        author = "postModelData.author.",
        categories = postModelData.categories,
    )

    fun mapListPostDataToListPostUi(listPosts:List<PostsModelDataItem>) = listPosts.map {
        mapPostModelDataToPostUiModel(it)
    }
}
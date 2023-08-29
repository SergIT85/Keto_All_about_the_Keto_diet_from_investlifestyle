package ru.investlifestyle.app.data

import javax.inject.Inject
import ru.investlifestyle.app.data.models.categories.SaveCategories
import ru.investlifestyle.app.data.networkApi.PostsModelDataItem
import ru.investlifestyle.app.data.room.ChoiceSubjectEntity
import ru.investlifestyle.app.data.room.LikePostsDbModelEntity
import ru.investlifestyle.app.data.room.PostDbModelEntity
import ru.investlifestyle.app.data.room.UserNameEntity
import ru.investlifestyle.app.ui.models.PostUiModel
import ru.investlifestyle.app.ui.models.UserName

class PostMapper @Inject constructor() {

    fun mapUserNameEntityToUserName(userNameEntity: UserNameEntity) = UserName(
        userName = userNameEntity.userName
    )
    fun mapUserNameToUserNameEntity(userName: UserName) = UserNameEntity(
        userName = userName.userName
    )
    // fun mapListUserNameEntityToUserName

    fun mapLikePostDbModelToPostUiModel(dbLikePost: LikePostsDbModelEntity) = PostUiModel(
        id = dbLikePost.idPosts,
        link = dbLikePost.link,
        title = dbLikePost.title,
        content = dbLikePost.content,
        posterMediaLinkUrl = dbLikePost.posterMediaLinkUrl,
        protected = false,
        author = dbLikePost.author,
        categories = dbLikePost.categories
            .removeSurrounding("[", "]")
            .split(",")
            .map { it.toInt() },
        liked = dbLikePost.liked,
        modifiedGmt = dbLikePost.modifiedGmt
    )

    fun mapPostUiModelToLikePostDbModel(postUiModel: PostUiModel) = LikePostsDbModelEntity(
        idPosts = postUiModel.id,
        link = postUiModel.link,
        title = postUiModel.title,
        content = postUiModel.content,
        author = postUiModel.author,
        categories = postUiModel.categories.toString(),
        posterMediaLinkUrl = postUiModel.posterMediaLinkUrl,
        liked = postUiModel.liked,
        modifiedGmt = postUiModel.modifiedGmt
    )

    fun mapListLikePostBdModelToListPostUiModel(list: List<LikePostsDbModelEntity>) = list.map {
        mapLikePostDbModelToPostUiModel(it)
    }

    fun mapSubjectSaveCategoriesToChoiceSubjectEntity(subjectSaveCategories: SaveCategories) =
        ChoiceSubjectEntity(
            id = subjectSaveCategories.idCategory,
            name = subjectSaveCategories.nameCategory,
            type = subjectSaveCategories.typeCategory,
            selected = subjectSaveCategories.selected
        )

    fun mapListSubjectCategoryToListSubjectEntity(list: List<SaveCategories>) = list.map {
        mapSubjectSaveCategoriesToChoiceSubjectEntity(it)
    }

    fun mapChoiceSubjectEntityToSubjectSaveCategories(choiceSubjectEntity: ChoiceSubjectEntity) =
        SaveCategories(
            idCategory = choiceSubjectEntity.id,
            nameCategory = choiceSubjectEntity.name,
            typeCategory = choiceSubjectEntity.type,
            selected = choiceSubjectEntity.selected
        )

    fun mapListChoiceSubjectEntityToListSubjectSaveCategories(list: List<ChoiceSubjectEntity>) =
        list.map {
            mapChoiceSubjectEntityToSubjectSaveCategories(it)
        }

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
        categories = postModelData.categories
            .removeSurrounding("[", "]")
            .split(",")
            .map { it.toInt() },
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
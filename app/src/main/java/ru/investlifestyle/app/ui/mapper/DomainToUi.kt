package ru.investlifestyle.app.ui.mapper

import ru.investlifestyle.app.domain.models.PostModel
import ru.investlifestyle.app.domain.models.SaveCategories
import ru.investlifestyle.app.domain.models.UserName
import ru.investlifestyle.app.ui.models.PostUiModel
import ru.investlifestyle.app.ui.models.SaveCategoriesUi
import ru.investlifestyle.app.ui.models.UserNameUi

fun UserName.toUi(): UserNameUi =
    UserNameUi(userName)

fun SaveCategories.toUi(): SaveCategoriesUi {
    return SaveCategoriesUi(
        nameCategory = this.nameCategory,
        typeCategory = this.typeCategory,
        idCategory = this.idCategory,
        selected = this.selected
    )
}

fun PostModel.toUi(): PostUiModel {
    return PostUiModel(
        id = this.id,
        link = this.link,
        title = this.title,
        posterMediaLinkUrl = this.posterMediaLinkUrl,
        content = this.content,
        protected = this.protected,
        author = this.author,
        categories = this.categories,
        liked = this.liked,
        modifiedGmt = this.modifiedGmt
    )
}
fun List<PostModel>.toUi(): List<PostUiModel> {
    return this.map { postModel ->
        PostUiModel(
            id = postModel.id,
            link = postModel.link,
            title = postModel.title,
            posterMediaLinkUrl = postModel.posterMediaLinkUrl,
            content = postModel.content,
            protected = postModel.protected,
            author = postModel.author,
            categories = postModel.categories,
            liked = postModel.liked,
            modifiedGmt = postModel.modifiedGmt
        )
    }
}
package ru.investlifestyle.app.data.mappers

import ru.investlifestyle.app.data.models.categories.SaveCategoriesData
import ru.investlifestyle.app.data.networkApi.PostsModelDataItem
import ru.investlifestyle.app.data.room.ChoiceSubjectEntity
import ru.investlifestyle.app.data.room.PostDbModelEntity
import ru.investlifestyle.app.data.room.UserNameEntity
import ru.investlifestyle.app.domain.models.PostModel
import ru.investlifestyle.app.domain.models.SaveCategories
import ru.investlifestyle.app.domain.models.UserName

fun UserNameEntity.toDomain(): UserName =
    UserName(userName)

fun PostsModelDataItem.toDomain(): PostModel {
    return PostModel(
        id = this.id,
        link = this.link,
        title = this.title.rendered,
        posterMediaLinkUrl = getImageUrlFromYoastHead(this.yoast_head),
        content = this.content.rendered,
        protected = this.content.protected,
        author = this.author.toString(),
        categories = this.categories,
        modifiedGmt = this.modified_gmt
    )
}

fun PostDbModelEntity.toDomain(): PostModel {
    return PostModel(
        id = this.id,
        link = this.link,
        title = this.title,
        posterMediaLinkUrl = this.posterMediaLinkUrl,
        content = this.content,
        protected = this.protected,
        author = "this.author.",
        categories = this.categories
            .removeSurrounding("[", "]")
            .split(",")
            .map { it.toInt() },
        modifiedGmt = this.modifiedGmt
    )
}

fun SaveCategoriesData.toDomain(): SaveCategories {
    return SaveCategories(
        nameCategory = this.nameCategory,
        typeCategory = this.typeCategory,
        idCategory = this.idCategory,
        selected = this.selected
    )
}

fun ChoiceSubjectEntity.toDomain(): SaveCategories {
    return SaveCategories(
        nameCategory = this.name,
        typeCategory = this.type,
        idCategory = this.id,
        selected = this.selected
    )
}

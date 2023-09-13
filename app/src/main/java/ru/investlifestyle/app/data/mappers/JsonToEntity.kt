package ru.investlifestyle.app.data.mappers

import ru.investlifestyle.app.data.networkApi.PostsModelDataItem
import ru.investlifestyle.app.data.room.PostDbModelEntity

class JsonToEntity {

    fun PostsModelDataItem.toEntity(): PostDbModelEntity {
        return PostDbModelEntity(
            id = this.id,
            link = this.link,
            title = this.title.rendered,
            posterMediaLinkUrl = getImageUrlFromYoastHead(this.yoast_head),
            content = this.content.rendered,
            protected = this.content.protected,
            author = "this.author.",
            categories = this.categories.toString(),
            modifiedGmt = this.modified_gmt
        )
    }
}
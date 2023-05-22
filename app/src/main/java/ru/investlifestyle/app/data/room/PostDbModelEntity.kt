package ru.investlifestyle.app.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "post_items")
data class PostDbModelEntity(
    @PrimaryKey
    var id: Int = 0,
    var link: String = "",
    var title: String = "",
    var posterMediaLinkUrl: String = "",
    var content: String = "",
    var protected: Boolean = false,
    var author: String = "",
    var categories: String = "",
    var liked: Boolean = false,
    var modifiedGmt: String = ""
) {
    constructor() : this(
        0,
        "",
        "",
        "",
        "",
        false,
        "",
        "",
        false,
        ""
    )
}

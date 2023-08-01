package ru.investlifestyle.app.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "like_posts")
data class LikePostsDbModelEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val idPosts: Int,
    val link: String,
    val title: String,
    val content: String,
    val author: String,
    val categories: String,
    val posterMediaLinkUrl: String,
    val liked: Boolean,
    val modifiedGmt: String
)

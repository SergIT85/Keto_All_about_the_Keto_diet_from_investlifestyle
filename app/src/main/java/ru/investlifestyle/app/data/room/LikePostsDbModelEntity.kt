package ru.investlifestyle.app.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "like_posts")
data class LikePostsDbModelEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val idPosts: Int,
    val title: String,
    val content: String,
    val liked: Boolean,
)

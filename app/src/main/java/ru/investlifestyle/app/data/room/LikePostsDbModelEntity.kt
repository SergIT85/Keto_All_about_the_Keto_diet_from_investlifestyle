package ru.investlifestyle.app.data.room

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "like_posts",
    indices = [
        Index("id", unique = true)
    ]
)
data class LikePostsDbModelEntity(
    @PrimaryKey
    val id: Int,
    val isChecked: Boolean
)

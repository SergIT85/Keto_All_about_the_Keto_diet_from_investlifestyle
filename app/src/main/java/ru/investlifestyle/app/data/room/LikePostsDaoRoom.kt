package ru.investlifestyle.app.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LikePostsDaoRoom {

    @Insert
    suspend fun insertLikePost(likePostsDbModelEntity: LikePostsDbModelEntity)


    @Query("SELECT * FROM like_posts WHERE id = :postId")
    fun getLikePostById(postId: Int): Flow<LikePostsDbModelEntity?>
}
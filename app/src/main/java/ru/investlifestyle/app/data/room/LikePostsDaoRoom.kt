package ru.investlifestyle.app.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LikePostsDaoRoom {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLikePost(likePostsDbModelEntity: LikePostsDbModelEntity)

    @Query("SELECT * FROM like_posts WHERE id = :postId LIMIT 1")
    suspend fun getLikePostById(postId: Int): LikePostsDbModelEntity

    @Query("SELECT * FROM like_posts")
    fun getAllLikePosts(): Flow<LikePostsDbModelEntity>
}
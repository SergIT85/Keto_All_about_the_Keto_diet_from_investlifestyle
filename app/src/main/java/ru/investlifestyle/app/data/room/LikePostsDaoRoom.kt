package ru.investlifestyle.app.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LikePostsDaoRoom {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLikePost(likePostsDbModelEntity: LikePostsDbModelEntity)

    @Query("SELECT * FROM like_posts WHERE id = :postId LIMIT 1")
    suspend fun getLikePostById(postId: Int): LikePostsDbModelEntity

    @Query("SELECT * FROM like_posts")
    suspend fun getAllLikePosts(): List<LikePostsDbModelEntity>

    @Query("DELETE FROM post_items WHERE id=:postItemId")
    suspend fun deleteLikePostById(postItemId: Int)
}
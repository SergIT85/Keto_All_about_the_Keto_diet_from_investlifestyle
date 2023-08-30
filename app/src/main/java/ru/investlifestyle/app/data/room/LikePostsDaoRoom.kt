package ru.investlifestyle.app.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LikePostsDaoRoom {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLikePost(likePostsDbModelEntity: LikePostsDbModelEntity)

    @Query("SELECT * FROM like_posts WHERE idPosts = :postId LIMIT 1")
    suspend fun getLikePostById(postId: Int): LikePostsDbModelEntity

    @Query("SELECT EXISTS(SELECT * FROM like_posts WHERE idPosts = :postId)")
    suspend fun getLikePostByIdBoolean(postId: Int): Boolean

    @Query("SELECT * FROM like_posts")
    suspend fun getAllLikePosts(): List<LikePostsDbModelEntity>

    @Query("DELETE FROM like_posts WHERE idPosts =:postItemId")
    suspend fun deleteLikePostById(postItemId: Int)

    @Query("SELECT(SELECT COUNT(*) FROM like_posts) == 0")
    suspend fun isEmpty(): Boolean
}
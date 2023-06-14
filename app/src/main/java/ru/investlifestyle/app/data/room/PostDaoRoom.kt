package ru.investlifestyle.app.data.room

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.*

@Dao
interface PostDaoRoom {

    @Query("SELECT * FROM post_items")
    fun getPostListPagingSource(): PagingSource<Int, PostDbModelEntity>

    @Query("SELECT * FROM post_items WHERE id=:postItemId LIMIT 1")
    fun getPostItemById(postItemId: Int): LiveData<PostDbModelEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(listPosts: List<PostDbModelEntity>)

    @Query("DELETE FROM post_items WHERE id=:postItemId")
    suspend fun deleteById(postItemId: Int)

    @Query("DELETE FROM post_items")
    suspend fun clear()

    @Transaction
    suspend fun transaction(listPosts: List<PostDbModelEntity>) {
        clear()
        save(listPosts)
    }

}
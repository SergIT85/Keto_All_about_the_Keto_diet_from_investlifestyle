package ru.investlifestyle.app.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserNameDaoRoom {
    @Query("SELECT * FROM user_name LIMIT 1")
    suspend fun getUserName(): UserNameEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(userName: UserNameEntity)

    @Query("SELECT(SELECT COUNT(*) FROM user_name) == 0")
    suspend fun isEmpty(): Boolean
}
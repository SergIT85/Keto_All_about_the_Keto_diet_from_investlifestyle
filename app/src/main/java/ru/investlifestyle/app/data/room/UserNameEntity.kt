package ru.investlifestyle.app.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_name")
data class UserNameEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var userName: String
)

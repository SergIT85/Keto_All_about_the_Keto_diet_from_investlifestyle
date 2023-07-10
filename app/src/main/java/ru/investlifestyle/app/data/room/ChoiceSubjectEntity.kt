package ru.investlifestyle.app.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "choice_subject")
data class ChoiceSubjectEntity(
    @PrimaryKey(autoGenerate = true)
    var idNumder: Int = 0,
    var id: Int,
    var name: String,
    var type: String,
    var selected: Boolean,
)

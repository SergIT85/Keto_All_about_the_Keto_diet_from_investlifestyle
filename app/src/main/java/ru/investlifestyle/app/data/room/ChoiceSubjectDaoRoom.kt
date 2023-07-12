package ru.investlifestyle.app.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ChoiceSubjectDaoRoom {
    @Query("SELECT * FROM choice_subject")
    fun getAllSubject(): Flow<List<ChoiceSubjectEntity>>

    @Query("SELECT * FROM choice_subject WHERE id=:subjectId LIMIT 1")
    suspend fun getSingleSubjectById(subjectId: Int): ChoiceSubjectEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(listSubject: List<ChoiceSubjectEntity>)

    @Query("UPDATE choice_subject SET selected=:selected WHERE id=:id")
    suspend fun updateSubject(selected: Boolean, id: Int)

    @Query("SELECT(SELECT COUNT(*) FROM choice_subject) == 0")
    suspend fun isEmpty(): Boolean
}
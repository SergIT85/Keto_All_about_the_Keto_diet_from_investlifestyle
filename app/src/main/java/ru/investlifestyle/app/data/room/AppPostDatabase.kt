package ru.investlifestyle.app.data.room

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        PostDbModelEntity::class,
        ChoiceSubjectEntity::class,
        LikePostsDbModelEntity::class,
        UserNameEntity::class
    ],
    version = 1, exportSchema = false
)
abstract class AppPostDatabase : RoomDatabase() {

    abstract fun postDaoRoom(): PostDaoRoom
    abstract fun subjectChoiceRoom(): ChoiceSubjectDaoRoom
    abstract fun likePostDaoRoom(): LikePostsDaoRoom
    abstract fun userNameDaoRoom(): UserNameDaoRoom

    companion object {
        private var INSTANCE: AppPostDatabase? = null
        private val LOCK = Any()
        private const val NAME_DB = "my_database"

        fun getInstanceDb(application: Application): AppPostDatabase {
            INSTANCE?.let {
                return it
            }
            synchronized(LOCK) {
                INSTANCE?.let {
                    return it
                }
                val db = Room.databaseBuilder(
                    application,
                    AppPostDatabase::class.java,
                    NAME_DB
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = db
                return db
            }
        }
    }
}
package ru.investlifestyle.app.di

import android.app.Application
import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.investlifestyle.app.data.repository.PostsRepositoryImpl
import ru.investlifestyle.app.data.room.AppPostDatabase
import ru.investlifestyle.app.data.room.PostDaoRoom
import ru.investlifestyle.app.domain.PostRepositoryInterface

@Module
interface DataModule {
    @Binds
    fun bindPostRepository(impl: PostsRepositoryImpl): PostRepositoryInterface

    companion object {
        @ApplicationScope
        @Provides
        fun provideDao(
            application: Application
        ): PostDaoRoom {
            return AppPostDatabase.getInstanceDb(application).postDaoRoom()
        }
    }
}
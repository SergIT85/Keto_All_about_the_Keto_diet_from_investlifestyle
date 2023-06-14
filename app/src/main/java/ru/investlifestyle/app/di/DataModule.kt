package ru.investlifestyle.app.di

import android.app.Application
import androidx.paging.ExperimentalPagingApi
import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.investlifestyle.app.data.repository.PostsRepositoryImpl
import ru.investlifestyle.app.data.room.AppPostDatabase
import ru.investlifestyle.app.data.room.PostDaoRoom
import ru.investlifestyle.app.domain.PostRepositoryInterface
import javax.inject.Singleton


@ExperimentalPagingApi
@Module
interface DataModule {

    @Singleton
    @Binds
    fun bindPostRepository(impl: PostsRepositoryImpl): PostRepositoryInterface


    companion object {
        //@ApplicationScope
        @Provides
        fun provideDao(
            application: Application
        ): PostDaoRoom {
            return AppPostDatabase.getInstanceDb(application).postDaoRoom()
        }
    }
}
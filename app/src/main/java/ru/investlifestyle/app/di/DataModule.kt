package ru.investlifestyle.app.di

import android.app.Application
import androidx.paging.ExperimentalPagingApi
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import ru.investlifestyle.app.data.repository.PostsRepositoryImpl
import ru.investlifestyle.app.data.repository.UserNameRepositoryImpl
import ru.investlifestyle.app.data.room.AppPostDatabase
import ru.investlifestyle.app.data.room.ChoiceSubjectDaoRoom
import ru.investlifestyle.app.data.room.LikePostsDaoRoom
import ru.investlifestyle.app.data.room.PostDaoRoom
import ru.investlifestyle.app.data.room.UserNameDaoRoom
import ru.investlifestyle.app.domain.repository.PostRepositoryInterface
import ru.investlifestyle.app.domain.repository.UserNameRepositoryInterface

@ExperimentalPagingApi
@Module
interface DataModule {

    @Singleton
    @Binds
    fun bindPostRepository(impl: PostsRepositoryImpl): PostRepositoryInterface

    @Singleton
    @Binds
    fun bindUserNameRepository(impl: UserNameRepositoryImpl): UserNameRepositoryInterface
    companion object {
        // @ApplicationScope
        @Provides
        fun provideDao(
            application: Application
        ): PostDaoRoom {
            return AppPostDatabase.getInstanceDb(application).postDaoRoom()
        }
        @Provides
        fun provideDaoSubject(
            application: Application
        ): ChoiceSubjectDaoRoom {
            return AppPostDatabase.getInstanceDb(application).subjectChoiceRoom()
        }
        @Provides
        fun provideLikePostDao(
            application: Application
        ): LikePostsDaoRoom {
            return AppPostDatabase.getInstanceDb(application).likePostDaoRoom()
        }

        @Provides
        fun provideUserNameDao(
            application: Application
        ): UserNameDaoRoom {
            return AppPostDatabase.getInstanceDb(application).userNameDaoRoom()
        }
    }
}
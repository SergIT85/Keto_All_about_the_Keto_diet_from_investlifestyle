package ru.investlifestyle.app.di

import dagger.Binds
import dagger.Module
import ru.investlifestyle.app.data.repository.PostsRepositoryImpl
import ru.investlifestyle.app.domain.PostRepositoryInterface

@Module
interface DataModule {
    @Binds
    fun bindPostRepository(impl: PostsRepositoryImpl): PostRepositoryInterface
}
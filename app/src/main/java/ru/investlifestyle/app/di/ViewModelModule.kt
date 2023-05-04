package ru.investlifestyle.app.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.investlifestyle.app.ui.dashboard.TopicsViewModel
import ru.investlifestyle.app.ui.home.HomeViewModel
import ru.investlifestyle.app.ui.notifications.NotificationsViewModel
import ru.investlifestyle.app.ui.post.PostViewModel

@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(TopicsViewModel::class)
    @Binds
    fun bindTopicsViewModel(impl: TopicsViewModel): ViewModel

    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    @Binds
    fun bindHomeViewModel(impl: HomeViewModel): ViewModel

    @IntoMap
    @ViewModelKey(NotificationsViewModel::class)
    @Binds
    fun bindNotificationsViewModel(impl: NotificationsViewModel): ViewModel

    @IntoMap
    @ViewModelKey(PostViewModel::class)
    @Binds
    fun bindPostViewModel(impl: PostViewModel): ViewModel
}
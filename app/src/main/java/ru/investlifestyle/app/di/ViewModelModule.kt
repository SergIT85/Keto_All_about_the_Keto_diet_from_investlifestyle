package ru.investlifestyle.app.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.investlifestyle.app.ui.home.HomeViewModel
import ru.investlifestyle.app.ui.notifications.NotificationsViewModel
import ru.investlifestyle.app.ui.post.PostViewModel
import ru.investlifestyle.app.ui.subject.SubjectTopicsViewModel

@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(SubjectTopicsViewModel::class)
    @Binds
    fun bindTopicsViewModel(impl: SubjectTopicsViewModel): ViewModel

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
package ru.investlifestyle.app.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.investlifestyle.app.ui.choice.ChoiceViewModel
import ru.investlifestyle.app.ui.home.HomeViewModel
import ru.investlifestyle.app.ui.notifications.NotificationsViewModel
import ru.investlifestyle.app.ui.post.PostViewModel
import ru.investlifestyle.app.ui.subject.SubjectTopicsViewModel
import ru.investlifestyle.app.ui.theme.ThemeViewModel

@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(ThemeViewModel::class)
    @Binds
    fun bindThemeViewModel(impl: ThemeViewModel): ViewModel

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

    @IntoMap
    @ViewModelKey(ChoiceViewModel::class)
    @Binds
    fun bindChoiceViewModel(impl: ChoiceViewModel): ViewModel
}
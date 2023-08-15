package ru.investlifestyle.app.di

import android.app.Application
import androidx.paging.ExperimentalPagingApi
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton
import ru.investlifestyle.app.MainActivity
import ru.investlifestyle.app.ui.choice.ChoiceFragment
import ru.investlifestyle.app.ui.home.HomeFragment
import ru.investlifestyle.app.ui.notifications.NotificationsFragment
import ru.investlifestyle.app.ui.post.PostActivity
import ru.investlifestyle.app.ui.post.PostFragment
import ru.investlifestyle.app.ui.subject.SubjectTopicsFragment
import ru.investlifestyle.app.ui.subject.SubjectTopicsViewModel
import ru.investlifestyle.app.ui.theme.ThemeFragment

@ExperimentalPagingApi
@Singleton
@Component(
    modules = [ApiRequestModule::class, ViewModelModule::class, DataModule::class]
)
interface AppComponent {

    // Activities
    fun inject(activity: MainActivity)
    fun inject(activity: PostActivity)

    // Fragments
    fun inject(fragment: ThemeFragment)
    fun inject(fragment: SubjectTopicsFragment)
    fun inject(fragment: HomeFragment)
    fun inject(fragment: NotificationsFragment)
    fun inject(fragment: PostFragment)
    fun inject(fragment: ChoiceFragment)

    fun inject(viewModel: SubjectTopicsViewModel)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application
        ): AppComponent
    }
}
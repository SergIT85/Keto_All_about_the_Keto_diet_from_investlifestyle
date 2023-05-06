package ru.investlifestyle.app.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import ru.investlifestyle.app.App
import ru.investlifestyle.app.MainActivity
import ru.investlifestyle.app.ui.dashboard.TopicsFragment
import ru.investlifestyle.app.ui.dashboard.TopicsViewModel
import ru.investlifestyle.app.ui.home.HomeFragment
import ru.investlifestyle.app.ui.notifications.NotificationsFragment
import ru.investlifestyle.app.ui.post.PostActivity
import ru.investlifestyle.app.ui.post.PostFragment
import javax.inject.Singleton

@Singleton
@Component(
    modules = [ApiRequestModule::class, ViewModelModule::class, DataModule::class]
)
interface AppComponent {

    //Activities
    fun inject(activity: MainActivity)
    fun inject(activity: PostActivity)

    //Fragments
    fun inject(fragment: TopicsFragment)
    fun inject(fragment: HomeFragment)
    fun inject(fragment: NotificationsFragment)
    fun inject(fragment: PostFragment)

    fun inject(viewModel: TopicsViewModel)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application
        ): AppComponent
    }
}
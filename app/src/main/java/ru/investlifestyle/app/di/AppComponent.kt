package ru.investlifestyle.app.di

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import dagger.BindsInstance
import dagger.Component
import ru.investlifestyle.app.App
import ru.investlifestyle.app.MainActivity
import ru.investlifestyle.app.ui.dashboard.TopicsFragment
import ru.investlifestyle.app.ui.dashboard.TopicsViewModel
import ru.investlifestyle.app.ui.home.HomeFragment
import ru.investlifestyle.app.ui.notifications.NotificationsFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    //Activities
    fun inject(activity: MainActivity)

    //Fragments
    fun inject(fragment: TopicsFragment)
    fun inject(fragment: HomeFragment)
    fun inject(fragment: NotificationsFragment)

    fun inject(viewModel: TopicsViewModel)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: App): Builder

        fun build(): AppComponent
    }

}
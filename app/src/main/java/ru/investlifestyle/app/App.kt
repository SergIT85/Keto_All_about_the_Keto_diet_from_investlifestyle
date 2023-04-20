package ru.investlifestyle.app

import android.app.Application
import ru.investlifestyle.app.di.AppComponent
import ru.investlifestyle.app.di.DaggerAppComponent

class App: Application() {

    val daggerAppComponent by lazy {
        DaggerAppComponent.builder().application(this).build()
    }
}
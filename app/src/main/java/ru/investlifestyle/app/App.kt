package ru.investlifestyle.app

import android.app.Application
import ru.investlifestyle.app.di.DaggerAppComponent

class App: Application() {

    val daggerAppComponent by lazy {
        DaggerAppComponent.factory().create(this)
    }
}
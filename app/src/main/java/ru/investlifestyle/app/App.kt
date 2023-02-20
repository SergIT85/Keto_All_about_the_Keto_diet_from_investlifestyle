package ru.investlifestyle.app

import android.app.Application
import ru.investlifestyle.app.di.AppComponent
import ru.investlifestyle.app.di.DaggerAppComponent

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        //init(this)
        DaggerAppComponent.builder().application(this).build()

    }



    fun init(app: App) {
        DaggerAppComponent.builder().application(app).build()//тут возможно надо будте добавить inject(app) и реализовать это... но посмотрим.
    }
}
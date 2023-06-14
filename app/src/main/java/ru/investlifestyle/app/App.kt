package ru.investlifestyle.app

import android.app.Application
import androidx.paging.ExperimentalPagingApi
import ru.investlifestyle.app.di.DaggerAppComponent

class App: Application() {

    @ExperimentalPagingApi
    val daggerAppComponent by lazy {
        DaggerAppComponent.factory().create(this)
    }
}
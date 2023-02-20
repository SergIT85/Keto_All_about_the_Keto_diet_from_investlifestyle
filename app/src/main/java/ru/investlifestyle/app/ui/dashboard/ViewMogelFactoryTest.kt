package ru.investlifestyle.app.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.investlifestyle.app.data.networkApi.examin.Repo

class ViewMogelFactoryTest(private val repo: Repo): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TopicsViewModel::class.java)) {
            return TopicsViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}
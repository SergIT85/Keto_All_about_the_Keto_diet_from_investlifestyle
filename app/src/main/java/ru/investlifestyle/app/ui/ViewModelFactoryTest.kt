package ru.investlifestyle.app.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.investlifestyle.app.data.networkApi.examin.Repo
import ru.investlifestyle.app.ui.dashboard.TopicsViewModel
import javax.inject.Inject
import javax.inject.Provider

class ViewModelFactoryTest @Inject constructor(
    //private val repo: Repo - учесть что репозиторий сюда не передаётся
    private val viewModelProvider: @JvmSuppressWildcards Map<Class<out ViewModel>, Provider<ViewModel>>
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return viewModelProvider[modelClass]?.get() as T
    }
}
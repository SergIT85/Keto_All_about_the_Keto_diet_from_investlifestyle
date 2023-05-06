package ru.investlifestyle.app.ui.home

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.investlifestyle.app.domain.usecase.GetQuotesUseCase
import ru.investlifestyle.app.domain.usecase.LoadPostsUseCase
import ru.investlifestyle.app.ui.models.PostUiModel
import ru.investlifestyle.app.ui.post.LoadPostState
import javax.inject.Inject

@SuppressLint("CheckResult")
class HomeViewModel @Inject constructor(
    private val loadPostsUseCase: LoadPostsUseCase,
    private val getQuotesUseCase: GetQuotesUseCase
) : ViewModel() {

    private var _postsListViewModel = MutableStateFlow<StateListPosts>(StateListPosts.Load)
    val postsListViewModel = _postsListViewModel.asStateFlow()

    private var _quotes = MutableLiveData<String>()
    val quotes: LiveData<String>
        get() = _quotes

    init {
        getPostList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ it ->
                _postsListViewModel.value = StateListPosts.Loaded(it)
            }, { error ->
                _postsListViewModel.value = StateListPosts.Error(error.toString())
            })
        getQuotes()
    }

    fun getPostList(): Single<List<PostUiModel>> {
        return loadPostsUseCase.getMainPostList(10)
    }

    private fun getQuotes() {
        _quotes.value = getQuotesUseCase.getQuotes()
    }

}
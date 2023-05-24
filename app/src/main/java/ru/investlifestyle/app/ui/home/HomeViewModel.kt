package ru.investlifestyle.app.ui.home

import android.annotation.SuppressLint
import androidx.lifecycle.*
import androidx.paging.cachedIn
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.investlifestyle.app.domain.usecase.GetPostPagingSourceUseCase
import ru.investlifestyle.app.domain.usecase.GetQuotesUseCase
import ru.investlifestyle.app.domain.usecase.LoadPostsUseCase
import ru.investlifestyle.app.ui.models.PostUiModel
import ru.investlifestyle.app.ui.post.LoadPostState
import javax.inject.Inject

@SuppressLint("CheckResult")
class HomeViewModel @Inject constructor(
    private val loadPostsUseCase: LoadPostsUseCase,
    private val getQuotesUseCase: GetQuotesUseCase,
    private val getPostPagingSourceUseCase: GetPostPagingSourceUseCase
) : ViewModel() {

    private var _postsListViewModel = MutableStateFlow<StateListPosts>(StateListPosts.Load)
    val postsListViewModel = _postsListViewModel.asStateFlow()

    private var _quotes = MutableLiveData<String>()
    val quotes: LiveData<String>
        get() = _quotes

    private val _posts = MutableLiveData(0)
    val posts = _posts.switchMap {
        getPostPagingSourceUseCase.getPostPagingSource().cachedIn(viewModelScope)
    }

    init {
        //getPostList()
        getQuotes()
    }

    /*fun getPostListPagingSource() {
        viewModelScope.launch {
            try {

            }
        }
    }*/

    suspend fun getPostList(): List<PostUiModel> {
        return loadPostsUseCase.getMainPostList(1)
    }

    private fun getQuotes() {
        _quotes.value = getQuotesUseCase.getQuotes()
    }

}
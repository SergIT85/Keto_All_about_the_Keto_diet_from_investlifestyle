package ru.investlifestyle.app.ui.home

import android.annotation.SuppressLint
import androidx.lifecycle.*
import androidx.paging.cachedIn
import javax.inject.Inject
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.investlifestyle.app.domain.usecase.FillingDbInitUseCase
import ru.investlifestyle.app.domain.usecase.GetPostPagingRemoteMediatorUseCase
import ru.investlifestyle.app.domain.usecase.GetQuotesUseCase
import ru.investlifestyle.app.domain.usecase.LoadPostsUseCase
import ru.investlifestyle.app.ui.models.PostUiModel

@SuppressLint("CheckResult")
class HomeViewModel @Inject constructor(
    private val loadPostsUseCase: LoadPostsUseCase,
    private val getQuotesUseCase: GetQuotesUseCase,
    private val getPostPagingRemoteMediatorUseCase: GetPostPagingRemoteMediatorUseCase,
    private val fillingDbInitUseCase: FillingDbInitUseCase
) : ViewModel() {

    private var _postsListViewModel = MutableStateFlow<StateListPosts>(StateListPosts.Load)
    val postsListViewModel = _postsListViewModel.asStateFlow()

    private var _quotes = MutableLiveData<String>()
    val quotes: LiveData<String>
        get() = _quotes

    private val _posts = MutableLiveData(0)
    val posts = _posts.asFlow()
        .flatMapLatest {
            getPostPagingRemoteMediatorUseCase
                .getPostPagingRemoteMediator()
                .cachedIn(viewModelScope)
        }

    init {
        // getPostList()
        getQuotes()
        fillingDbInit()
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
    private fun fillingDbInit() {
        viewModelScope.launch {
            fillingDbInitUseCase.fillingDbInit()
        }
    }
}
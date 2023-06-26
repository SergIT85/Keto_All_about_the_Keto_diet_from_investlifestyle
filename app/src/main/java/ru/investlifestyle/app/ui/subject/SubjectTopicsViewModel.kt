package ru.investlifestyle.app.ui.subject

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.investlifestyle.app.data.networkApi.PostsModelDataItem
import ru.investlifestyle.app.domain.usecase.GetMainPostsListUseCase
import ru.investlifestyle.app.domain.usecase.LoadPostsUseCase
import ru.investlifestyle.app.domain.usecase.LoadSubjectPostsUseCase
import ru.investlifestyle.app.ui.home.StateListPosts
import ru.investlifestyle.app.ui.models.PostUiModel
import javax.inject.Inject

@SuppressLint("CheckResult")
class SubjectTopicsViewModel @Inject constructor(
    private val loadPostsUseCase: LoadPostsUseCase,
    private val loadSubjectPostsUseCase: LoadSubjectPostsUseCase
) : ViewModel() {

    private var _postListViewModel = MutableStateFlow<StateListPosts>(StateListPosts.Load)
    val postListViewModel = _postListViewModel

    private var _loadSubjectPost = MutableLiveData<List<PostUiModel>>()
    val loadSubjectPost: LiveData<List<PostUiModel>>
        get() = _loadSubjectPost


    init {
        viewModelScope.launch {
            //getPostsApi()
            load()
        }
        //loadSubjectPost()


    }

    private val _text = MutableLiveData<String>().apply {
        value = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
    }
    val text: LiveData<String> = _text

    private fun loadSubjectPost() {
        viewModelScope.launch {
            _loadSubjectPost.value = loadSubjectPostsUseCase
                .loadSubjectPosts(11, 1, 10, true)
        }

    }
    private suspend fun load () {
        _loadSubjectPost.value = loadPostsUseCase.getMainPostList(1)
    }

    @SuppressLint("LogNotTimber")
    suspend fun getPostsApi(): List<PostUiModel> {
        //val result = getPostsListUseCase.getPostsList(1)
        //Log.d("TopicsViewModel","${result.subscribe { it -> toString() }}")
        return loadPostsUseCase.getMainPostList(1)

    }
}
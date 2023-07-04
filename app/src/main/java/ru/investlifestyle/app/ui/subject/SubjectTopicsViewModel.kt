package ru.investlifestyle.app.ui.subject

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.investlifestyle.app.data.models.categories.SaveCategories
import ru.investlifestyle.app.domain.usecase.GetCategoriesUseCase
import ru.investlifestyle.app.domain.usecase.LoadPostsUseCase
import ru.investlifestyle.app.domain.usecase.LoadSubjectPostsUseCase
import ru.investlifestyle.app.domain.usecase.LoadSubjectTagsPostsUseCase
import ru.investlifestyle.app.ui.home.StateListPosts
import ru.investlifestyle.app.ui.models.PostUiModel

@SuppressLint("CheckResult")
class SubjectTopicsViewModel @Inject constructor(
    private val loadPostsUseCase: LoadPostsUseCase,
    private val loadSubjectPostsUseCase: LoadSubjectPostsUseCase,
    private val loadSubjectPostsTagsUseCase: LoadSubjectTagsPostsUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {

    private var _postListViewModel = MutableStateFlow<StateListPosts>(StateListPosts.Load)
    val postListViewModel = _postListViewModel

    private var _loadSubjectPost = MutableLiveData<List<PostUiModel>>()
    val loadSubjectPost: LiveData<List<PostUiModel>>
        get() = _loadSubjectPost

    private var _loadHealthPost = MutableLiveData<List<PostUiModel>>()
    val loadHealthPost: LiveData<List<PostUiModel>>
        get() = _loadHealthPost

    private var _loadKetoCourses = MutableLiveData<List<PostUiModel>>()
    val loadKetoCourses: LiveData<List<PostUiModel>>
        get() = _loadKetoCourses

    private var _loadNutrition = MutableLiveData<List<PostUiModel>>()
    val loadNutrition: LiveData<List<PostUiModel>>
        get() = _loadNutrition

    private var _getCategories = MutableLiveData<List<SaveCategories>>()
    val getCategories: LiveData<List<SaveCategories>>
        get() = _getCategories

    private var _loadSubjectTagsPost = MutableLiveData<List<PostUiModel>>()
    val loadSubjectTagsPost: LiveData<List<PostUiModel>>
        get() = _loadSubjectTagsPost

    init {
        viewModelScope.launch {
            // getPostsApi()
            getCategories()
            load()
            loadSubjectTagsPosts()
        }
        // loadSubjectPost()
    }

    private suspend fun getCategories() {
        _getCategories.value = getCategoriesUseCase.getCategories()
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

    private suspend fun load() {
        val health = getCategories.value?.find { it.nameCategory == "Здоровье" }
        if (health != null) {
            _loadHealthPost.value =
                loadSubjectPostsUseCase.loadSubjectPosts(
                    health.idCategory,
                    1,
                    10,
                    true
                )
        }
        _loadSubjectPost.value = loadSubjectPostsUseCase
            .loadSubjectPosts(11, 1, 10, true)
    }

    private suspend fun loadSubjectTagsPosts() {
        val ketoTags = getCategories.value?.find { it.nameCategory == "Кето" }
        if (ketoTags != null) {
            _loadSubjectTagsPost.value = loadSubjectPostsTagsUseCase
                .loadSubjectTagsPosts(27, 1, 10, true)
        }
    }

    @SuppressLint("LogNotTimber")
    suspend fun getPostsApi(): List<PostUiModel> {
        // val result = getPostsListUseCase.getPostsList(1)
        // Log.d("TopicsViewModel","${result.subscribe { it -> toString() }}")
        return loadPostsUseCase.getMainPostList(1)
    }
}
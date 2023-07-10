package ru.investlifestyle.app.ui.subject

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import java.io.IOException
import javax.inject.Inject
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.HttpException
import ru.investlifestyle.app.data.repository.PostsRepositoryImpl.Companion.EVOLUTION
import ru.investlifestyle.app.data.repository.PostsRepositoryImpl.Companion.HEALTH
import ru.investlifestyle.app.data.repository.PostsRepositoryImpl.Companion.KETOCOURSES
import ru.investlifestyle.app.data.repository.PostsRepositoryImpl.Companion.NUTRITION
import ru.investlifestyle.app.domain.usecase.*
import ru.investlifestyle.app.ui.home.StateListPosts
import ru.investlifestyle.app.ui.models.PostUiModel

@OptIn(ExperimentalPagingApi::class)
@SuppressLint("CheckResult")
class SubjectTopicsViewModel @Inject constructor(
    private val loadPostsUseCase: LoadPostsUseCase,
    private val loadSubjectPostsUseCase: LoadSubjectPostsUseCase,
    private val loadSubjectPostsTagsUseCase: LoadSubjectTagsPostsUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val loadSubjectPostsFlowUseCase: LoadSubjectPostsFlowUseCase
) : ViewModel() {

    private var _getCategories =
        MutableStateFlow<StateListSubjects>(StateListSubjects.EmptyListSubjects)
    val allCategories: StateFlow<StateListSubjects> = _getCategories

    private suspend fun getCategories() {
        viewModelScope.launch {
            try {
                _getCategories.value =
                    StateListSubjects.FilledListSubjects(getCategoriesUseCase.getCategories())
            } catch (exception: Exception) {
                _getCategories.value = StateListSubjects.Error(exception.toString())
            }
        }
    }

    private var _postListViewModel = MutableStateFlow<StateListPosts>(StateListPosts.Load)
    val postListViewModel = _postListViewModel

    private var _loadSubjectPost = MutableLiveData<List<PostUiModel>>()
    val loadSubjectPost: LiveData<List<PostUiModel>>
        get() = _loadSubjectPost

    private val _loadHealthPost = MutableStateFlow<StateSubjectLoaded>(StateSubjectLoaded.Loading)
    val stateLoadHealthPost: StateFlow<StateSubjectLoaded> = _loadHealthPost

    private fun loadHealthPosts(category: Int) {
        viewModelScope.launch {
            try {
                _loadHealthPost.value =
                    StateSubjectLoaded.Loaded(
                        loadSubjectPostsFlowUseCase.loadSubjectPostsFlow(
                            category
                        )
                    )
            } catch (exception: IOException) {
                _loadHealthPost.value =
                    StateSubjectLoaded.Error(exception.toString())
            } catch (exception: HttpException) {
                _loadHealthPost.value =
                    StateSubjectLoaded.Error(exception.toString())
            }
        }
    }

    private fun notLoadHealthCategory(category: Int) {
        viewModelScope.launch {
            try {
                _loadHealthPost.value = StateSubjectLoaded.NotLoaded
            } catch (exception: Exception) {
                _loadHealthPost.value = StateSubjectLoaded.Error(exception.toString())
            }
        }
    }

    private fun <T> Flow<T>.mergeWith(another: Flow<T>): Flow<T> {
        return merge(this, another)
    }

    private var _loadKetoCourses = MutableLiveData<List<PostUiModel>>()
    val loadKetoCourses: LiveData<List<PostUiModel>>
        get() = _loadKetoCourses

    private var _loadNutrition = MutableLiveData<List<PostUiModel>>()
    val loadNutrition: LiveData<List<PostUiModel>>
        get() = _loadNutrition

    private var _loadEvolution = MutableLiveData<List<PostUiModel>>()
    val loadEvolution: LiveData<List<PostUiModel>>
        get() = _loadEvolution

    private var _loadSubjectTagsPost = MutableLiveData<List<PostUiModel>>()
    val loadSubjectTagsPost: LiveData<List<PostUiModel>>
        get() = _loadSubjectTagsPost

    init {
        viewModelScope.launch {
            // getPostsApi()
            getCategories()
            /*getCategories()
            loadPostsCategories()
            loadSubjectTagsPosts()*/
            loadPostsCategories()
        }
        // loadSubjectPost()
    }

    private fun stateSubjectLoaded() {
    }

    private val _text = MutableLiveData<String>().apply {
        value = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
    }
    val text: LiveData<String> = _text

    private fun loadSubjectPost() {
        viewModelScope.launch {
            _loadSubjectPost.value = loadSubjectPostsUseCase
                .loadSubjectPosts(11, 1, PERPAGE, true)
        }
    }

    private suspend fun loadPostsCategories() {
        allCategories.collect {
            when (it) {
                is StateListSubjects.FilledListSubjects -> {
                    val health = it.listSubjects.find { it.nameCategory == HEALTH }
                    val ketoCourses = it.listSubjects.find { it.nameCategory == KETOCOURSES }
                    val nutrition = it.listSubjects.find { it.nameCategory == NUTRITION }
                    val evolution = it.listSubjects.find { it.nameCategory == EVOLUTION }

                    if (health != null && health.selected == true) {
                        loadHealthPosts(health.idCategory)
                    } else {
                        health?.let { it1 -> notLoadHealthCategory(it1.idCategory) }
                    }
                    if (ketoCourses != null) {
                        _loadKetoCourses.value =
                            loadSubjectPostsUseCase.loadSubjectPosts(
                                ketoCourses.idCategory,
                                PAGE,
                                PERPAGE,
                                true
                            )
                    }
                    if (nutrition != null) {
                        _loadNutrition.value =
                            loadSubjectPostsUseCase.loadSubjectPosts(
                                nutrition.idCategory,
                                PAGE,
                                PERPAGE,
                                true
                            )
                    }
                    if (evolution != null) {
                        _loadEvolution.value =
                            loadSubjectPostsUseCase.loadSubjectPosts(
                                evolution.idCategory,
                                PAGE,
                                PERPAGE,
                                true
                            )
                    }
                }
                is StateListSubjects.EmptyListSubjects -> {
                }
                is StateListSubjects.Error -> {
                }
            }
        }
    }

    /*private suspend fun loadSubjectTagsPosts() {
        val ketoTags = getCategories.value?.find { it.nameCategory == TAGSKETO }
        if (ketoTags != null) {
            _loadSubjectTagsPost.value = loadSubjectPostsTagsUseCase
                .loadSubjectTagsPosts(ketoTags.idCategory, PAGE, PERPAGE, true)
        }
    }*/

    @SuppressLint("LogNotTimber")
    suspend fun getPostsApi(): List<PostUiModel> {
        // val result = getPostsListUseCase.getPostsList(1)
        // Log.d("TopicsViewModel","${result.subscribe { it -> toString() }}")
        return loadPostsUseCase.getMainPostList(1)
    }

    companion object {
        const val PERPAGE = 10
        const val PAGE = 1
    }
}
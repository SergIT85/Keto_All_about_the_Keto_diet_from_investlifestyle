package ru.investlifestyle.app.ui.subject

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import javax.inject.Inject
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.investlifestyle.app.domain.usecase.*
import ru.investlifestyle.app.ui.models.PostUiModel
import ru.investlifestyle.app.ui.subject.adapters.CategoryId

@OptIn(ExperimentalPagingApi::class)
@SuppressLint("CheckResult")
class SubjectTopicsViewModel @Inject constructor(
    private val loadSubjectPostsUseCase: LoadSubjectPostsUseCase,
    private val loadSubjectPostsTagsUseCase: LoadSubjectTagsPostsUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val fillingDbInitUseCase: FillingDbInitUseCase,
    private val getAllLikePosts: GetAllLikePostsUseCase

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

    private var _footerAdapterLiveDataHealth = MutableLiveData<CategoryId>()
    val footerAdapterLiveDataHealth: LiveData<CategoryId>
        get() = _footerAdapterLiveDataHealth

    private var _footerAdapterLiveDataEvolution = MutableLiveData<CategoryId>()
    val footerAdapterLiveDataEvolution: LiveData<CategoryId>
        get() = _footerAdapterLiveDataEvolution

    private var _footerAdapterLiveDataKetoCourses = MutableLiveData<CategoryId>()
    val footerAdapterLiveDataKetoCourses: LiveData<CategoryId>
        get() = _footerAdapterLiveDataKetoCourses

    private var _footerAdapterLiveDataNutrition = MutableLiveData<CategoryId>()
    val footerAdapterLiveDataNutrition: LiveData<CategoryId>
        get() = _footerAdapterLiveDataNutrition

    private var _footerAdapterLiveDataTagsKeto = MutableLiveData<CategoryId>()
    val footerAdapterLiveDataTagsKeto: LiveData<CategoryId>
        get() = _footerAdapterLiveDataTagsKeto

    private var _footerAdapterLiveDataTagsEducation = MutableLiveData<CategoryId>()
    val footerAdapterLiveDataTagsEducation: LiveData<CategoryId>
        get() = _footerAdapterLiveDataTagsEducation

    private var _footerAdapterLiveDataTagsUseful = MutableLiveData<CategoryId>()
    val footerAdapterLiveDataTagsUseful: LiveData<CategoryId>
        get() = _footerAdapterLiveDataTagsUseful

    private var _footerAdapterLiveDataTagsRecipes = MutableLiveData<CategoryId>()
    val footerAdapterLiveDataTagsRecipes: LiveData<CategoryId>
        get() = _footerAdapterLiveDataTagsRecipes

    private var _loadLikePosts = MutableLiveData<List<PostUiModel>>()
    val loadLikePosts: MutableLiveData<List<PostUiModel>>
        get() = _loadLikePosts

    private var _loadHealth = MutableLiveData<List<PostUiModel>>()
    val loadHealth: LiveData<List<PostUiModel>>
        get() = _loadHealth

    private var _loadKetoCourses = MutableLiveData<List<PostUiModel>>()
    val loadKetoCourses: LiveData<List<PostUiModel>>
        get() = _loadKetoCourses

    private var _loadNutrition = MutableLiveData<List<PostUiModel>>()
    val loadNutrition: LiveData<List<PostUiModel>>
        get() = _loadNutrition

    private var _loadEvolution = MutableLiveData<List<PostUiModel>>()
    val loadEvolution: LiveData<List<PostUiModel>>
        get() = _loadEvolution

    private var _loadTagsKeto = MutableLiveData<List<PostUiModel>>()
    val loadTagsKeto: LiveData<List<PostUiModel>>
        get() = _loadTagsKeto

    private var _loadTagsEducation = MutableLiveData<List<PostUiModel>>()
    val loadTagsEducation: LiveData<List<PostUiModel>>
        get() = _loadTagsEducation

    private var _loadTagsUseful = MutableLiveData<List<PostUiModel>>()
    val loadTagsUseful: LiveData<List<PostUiModel>>
        get() = _loadTagsUseful

    private var _loadTagsRecipes = MutableLiveData<List<PostUiModel>>()
    val loadTagsRecipes: LiveData<List<PostUiModel>>
        get() = _loadTagsRecipes

    init {
        viewModelScope.launch {
            if (allCategories.value is StateListSubjects.EmptyListSubjects) {
                fillingDbInit()
                getCategories()
            }
        }
    }

    suspend fun loadLikePost() {
        _loadLikePosts.value = getAllLikePosts.getAllLikePosts()
    }

    suspend fun loadPostsHealth(id: Int) {
        _loadHealth.value = loadSubjectPostsUseCase.loadSubjectPosts(id)
        _footerAdapterLiveDataHealth.value = CategoryId(id)
    }

    suspend fun loadPostsKetoCourses(id: Int) {
        _loadKetoCourses.value = loadSubjectPostsUseCase.loadSubjectPosts(id)
        _footerAdapterLiveDataKetoCourses.value = CategoryId(id)
    }

    suspend fun loadPostsNutrition(id: Int) {
        _loadNutrition.value = loadSubjectPostsUseCase.loadSubjectPosts(id)
        _footerAdapterLiveDataNutrition.value = CategoryId(id)
    }

    suspend fun loadPostsEvolution(id: Int) {
        _loadEvolution.value = loadSubjectPostsUseCase.loadSubjectPosts(id)
        _footerAdapterLiveDataEvolution.value = CategoryId(id)
    }

    suspend fun loadPostsTagsKeto(id: Int) {
        _loadTagsKeto.value = loadSubjectPostsTagsUseCase.loadSubjectTagsPosts(id)
        _footerAdapterLiveDataTagsKeto.value = CategoryId(id)
    }

    suspend fun loadPostsTagsEducation(id: Int) {
        _loadTagsEducation.value = loadSubjectPostsTagsUseCase.loadSubjectTagsPosts(id)
        _footerAdapterLiveDataTagsEducation.value = CategoryId(id)
    }

    suspend fun loadPostsTagsUseful(id: Int) {
        _loadTagsUseful.value = loadSubjectPostsTagsUseCase.loadSubjectTagsPosts(id)
        _footerAdapterLiveDataTagsUseful.value = CategoryId(id)
    }

    suspend fun loadPostsTagsRecipes(id: Int) {
        _loadTagsRecipes.value = loadSubjectPostsTagsUseCase.loadSubjectTagsPosts(id)
        _footerAdapterLiveDataTagsRecipes.value = CategoryId(id)
    }

    private suspend fun fillingDbInit() {
        fillingDbInitUseCase.fillingDbInit()
    }

    companion object {
        const val PERPAGE = 10
        const val PAGE = 1
    }
}
package ru.investlifestyle.app.ui.subject

import android.annotation.SuppressLint
import android.util.Log
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

@OptIn(ExperimentalPagingApi::class)
@SuppressLint("CheckResult")
class SubjectTopicsViewModel @Inject constructor(
    private val loadSubjectPostsUseCase: LoadSubjectPostsUseCase,
    private val loadSubjectPostsTagsUseCase: LoadSubjectTagsPostsUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val fillingDbInitUseCase: FillingDbInitUseCase
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

    suspend fun loadPostsHealth(id: Int) {
        _loadHealth.value =
            loadSubjectPostsUseCase.loadSubjectPosts(id)
    }

    suspend fun loadPostsKetoCourses(id: Int) {
        _loadKetoCourses.value =
            loadSubjectPostsUseCase.loadSubjectPosts(id)
    }

    suspend fun loadPostsNutrition(id: Int) {
        _loadNutrition.value =
            loadSubjectPostsUseCase.loadSubjectPosts(id)
    }

    suspend fun loadPostsEvolution(id: Int) {
        _loadEvolution.value =
            loadSubjectPostsUseCase.loadSubjectPosts(id)
    }

    suspend fun loadPostsTagsKeto(id: Int) {
        _loadTagsKeto.value =
            loadSubjectPostsTagsUseCase.loadSubjectTagsPosts(id)
    }

    suspend fun loadPostsTagsEducation(id: Int) {
        _loadTagsEducation.value =
            loadSubjectPostsTagsUseCase.loadSubjectTagsPosts(id)
    }

    suspend fun loadPostsTagsUseful(id: Int) {
        _loadTagsUseful.value =
            loadSubjectPostsTagsUseCase.loadSubjectTagsPosts(id)
    }

    suspend fun loadPostsTagsRecipes(id: Int) {
        _loadTagsRecipes.value =
            loadSubjectPostsTagsUseCase.loadSubjectTagsPosts(id)
    }

    @SuppressLint("LogNotTimber")
    private suspend fun loadPostsCategories() {
        allCategories.collect {
            when (it) {
                is StateListSubjects.FilledListSubjects -> {
                    it.listSubjects.collect {
                        /*val health = it.find { it.nameCategory == HEALTH }
                        if (health != null && health.selected == true) {
                            _loadHealth.value =
                                loadSubjectPostsUseCase.loadSubjectPosts(health.idCategory)
                            Log.d(
                                "https://investlifestyle.",
                                " ОТРАБОТКА loadPostsCategories() в " +
                                    "it.listSubjects.collect в SubjectTopicsViewModel"
                            )
                        }*/

                        val ketoCourses = it.find { it.nameCategory == KETOCOURSES }
                        if (ketoCourses != null && ketoCourses.selected) {
                            _loadKetoCourses.value =
                                loadSubjectPostsUseCase.loadSubjectPosts(ketoCourses.idCategory)
                        }

                        val nutrition = it.find { it.nameCategory == NUTRITION }
                        if (nutrition != null && nutrition.selected) {
                            _loadNutrition.value =
                                loadSubjectPostsUseCase.loadSubjectPosts(nutrition.idCategory)
                        }

                        val evolution = it.find { it.nameCategory == EVOLUTION }
                        if (evolution != null && evolution.selected) {
                            _loadEvolution.value =
                                loadSubjectPostsUseCase.loadSubjectPosts(evolution.idCategory)
                        }

                        val tagsKeto = it.find { it.nameCategory == TAGSKETO }
                        if (tagsKeto != null && tagsKeto.selected) {
                            _loadTagsKeto.value =
                                loadSubjectPostsTagsUseCase.loadSubjectTagsPosts(
                                    tagsKeto.idCategory
                                )
                        }

                        val tagsEducation = it.find { it.nameCategory == TAGSEDUCATION }
                        if (tagsEducation != null && tagsEducation.selected) {
                            _loadTagsEducation.value =
                                loadSubjectPostsTagsUseCase.loadSubjectTagsPosts(
                                    tagsEducation.idCategory
                                )
                        }

                        val tagsUseful = it.find { it.nameCategory == TAGSUSEFUL }
                        if (tagsUseful != null && tagsUseful.selected) {
                            _loadTagsUseful.value =
                                loadSubjectPostsTagsUseCase.loadSubjectTagsPosts(
                                    tagsUseful.idCategory
                                )
                        }

                        val tagsRecipes = it.find { it.nameCategory == TAGSRECIPES }
                        if (tagsRecipes != null && tagsRecipes.selected) {
                            _loadTagsRecipes.value =
                                loadSubjectPostsTagsUseCase.loadSubjectTagsPosts(
                                    tagsRecipes.idCategory
                                )
                        }
                    }
                }
                is StateListSubjects.EmptyListSubjects -> {
                    fillingDbInit()
                    Log.d(
                        "https://investlifestyle.",
                        " ОТРАБОТКА _fillingDbInit() в " +
                            "StateListSubjects.EmptyListSubjects в SubjectTopicsViewModel"
                    )
                }
                is StateListSubjects.Error -> {
                }
            }
        }
    }

    private suspend fun fillingDbInit() {
        fillingDbInitUseCase.fillingDbInit()
    }

    companion object {
        const val PERPAGE = 10
        const val PAGE = 1

        const val HEALTH = "Здоровье"
        const val KETOCOURSES = "Кето курс"
        const val NUTRITION = "Питание"
        const val EVOLUTION = "Развитие"
        const val TAGSKETO = "Кето"
        const val TAGSEDUCATION = "Обучение"
        const val TAGSUSEFUL = "Полезное"
        const val TAGSRECIPES = "Рецепты"
    }
}
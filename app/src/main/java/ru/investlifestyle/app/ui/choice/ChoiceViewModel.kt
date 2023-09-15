package ru.investlifestyle.app.ui.choice

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import ru.investlifestyle.app.domain.usecase.FillingDbInitUseCase
import ru.investlifestyle.app.domain.usecase.GetCategoriesUseCase
import ru.investlifestyle.app.domain.usecase.UpdateSubjectUseCase
import ru.investlifestyle.app.ui.mapper.toUi
import ru.investlifestyle.app.ui.subject.StateListSubjects

class ChoiceViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val updateSubjectUseCase: UpdateSubjectUseCase,
    private val fillingDbInitUseCase: FillingDbInitUseCase
) : ViewModel() {

    private var _getCategories =
        MutableStateFlow<StateListSubjects>(StateListSubjects.EmptyListSubjects)
    val allCategories: StateFlow<StateListSubjects> = _getCategories
    @SuppressLint("LogNotTimber")
    private fun getCategories() {
        viewModelScope.launch {
            try {
                getCategoriesUseCase.getCategories().collect { it ->

                    Log.d(
                        "saveCategories.toUi",
                        " ОТРАБОТКА fillingDbInit() в " +
                            "ChoiceViewModel"
                    )
                    _getCategories.value = StateListSubjects.FilledListSubjects(
                        flow {
                            emit(
                                it.map { saveCategories ->
                                    saveCategories.toUi()
                                }
                            )
                        }
                    )
                }

                        /*map { it ->
                                it.map { saveCategories ->
                                    saveCategories.toUi()
                                }
                            }*/
            } catch (exception: Exception) {
                _getCategories.value = StateListSubjects.Error(exception.toString())
            }
        }
    }

    fun updateSubject(selected: Boolean, id: Int) {
        viewModelScope.launch {
            updateSubjectUseCase.updateSubject(selected, id)
        }
    }
    @SuppressLint("LogNotTimber")
    private suspend fun fillingDbInit() {
        fillingDbInitUseCase.fillingDbInit()
        Log.d(
            "https://investlifestyle.",
            " ОТРАБОТКА fillingDbInit() в " +
                "ChoiceViewModel"
        )
    }

    init {
        viewModelScope.launch {
            if (allCategories.value is StateListSubjects.EmptyListSubjects) {
                fillingDbInit()
                getCategories()
            }
        }
    }
}
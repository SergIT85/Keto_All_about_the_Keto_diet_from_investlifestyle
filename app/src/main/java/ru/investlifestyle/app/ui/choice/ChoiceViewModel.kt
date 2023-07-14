package ru.investlifestyle.app.ui.choice

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.investlifestyle.app.domain.usecase.FillingDbInitUseCase
import ru.investlifestyle.app.domain.usecase.GetCategoriesForChoiceFragmentUseCase
import ru.investlifestyle.app.domain.usecase.UpdateSubjectUseCase
import ru.investlifestyle.app.ui.subject.StateListSubjects

class ChoiceViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesForChoiceFragmentUseCase,
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
                _getCategories.value =
                    StateListSubjects.FilledListSubjects(getCategoriesUseCase.getCategories())
                Log.d(
                    "https://investlifestyle.",
                    " ОТРАБОТКА _getCategories.value = в " +
                        "ChoiceViewModel"
                )
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
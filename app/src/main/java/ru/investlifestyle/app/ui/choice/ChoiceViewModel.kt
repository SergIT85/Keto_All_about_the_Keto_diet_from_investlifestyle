package ru.investlifestyle.app.ui.choice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.investlifestyle.app.domain.usecase.GetCategoriesUseCase
import ru.investlifestyle.app.domain.usecase.GetSingleSubjectByIdUseCase
import ru.investlifestyle.app.domain.usecase.UpdateSubjectUseCase
import ru.investlifestyle.app.ui.subject.StateListSubjects

class ChoiceViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getSingleSubjectByIdUseCase: GetSingleSubjectByIdUseCase,
    private val updateSubjectUseCase: UpdateSubjectUseCase
) : ViewModel() {

    private var _getCategories =
        MutableStateFlow<StateListSubjects>(StateListSubjects.EmptyListSubjects)
    val allCategories: StateFlow<StateListSubjects> = _getCategories
    private fun getCategories() {
        viewModelScope.launch {
            try {
                _getCategories.value =
                    StateListSubjects.FilledListSubjects(getCategoriesUseCase.getCategories())
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

    init {
        getCategories()
    }
}
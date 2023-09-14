package ru.investlifestyle.app.ui.subject

import kotlinx.coroutines.flow.Flow
import ru.investlifestyle.app.ui.models.SaveCategoriesUi

sealed class StateListSubjects {
    object EmptyListSubjects : StateListSubjects()
    data class FilledListSubjects(
        val listSubjects: Flow<List<SaveCategoriesUi>>
    ) : StateListSubjects()
    data class Error(val exception: String) : StateListSubjects()
}

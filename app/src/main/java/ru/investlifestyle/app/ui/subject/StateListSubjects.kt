package ru.investlifestyle.app.ui.subject

import kotlinx.coroutines.flow.Flow
import ru.investlifestyle.app.data.models.categories.SaveCategoriesData

sealed class StateListSubjects {
    object EmptyListSubjects : StateListSubjects()
    data class FilledListSubjects(
        val listSubjects: Flow<List<SaveCategoriesData>>
    ) : StateListSubjects()
    data class Error(val exception: String) : StateListSubjects()
}

package ru.investlifestyle.app.ui.subject

import kotlinx.coroutines.flow.Flow
import ru.investlifestyle.app.data.models.categories.SaveCategories

sealed class StateListSubjects {
    object EmptyListSubjects : StateListSubjects()
    data class FilledListSubjects(
        val listSubjects: Flow<List<SaveCategories>>
    ) : StateListSubjects()
    data class Error(val exception: String) : StateListSubjects()
}

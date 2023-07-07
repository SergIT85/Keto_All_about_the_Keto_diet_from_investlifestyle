package ru.investlifestyle.app.ui.subject

import ru.investlifestyle.app.data.models.categories.SaveCategories

sealed class StateListSubjects {
    object EmptyListSubjects : StateListSubjects()
    data class FilledListSubjects(val listSubjects: List<SaveCategories>) : StateListSubjects()
    data class Error(val exception: String) : StateListSubjects()
}

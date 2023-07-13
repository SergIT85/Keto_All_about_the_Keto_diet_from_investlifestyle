package ru.investlifestyle.app.ui.subject

import ru.investlifestyle.app.ui.models.PostUiModel

sealed class StateSubjectLoaded {
    object NotLoaded : StateSubjectLoaded()
    object Loading : StateSubjectLoaded()
    data class Loaded(val listPosts: List<PostUiModel>) : StateSubjectLoaded()
    data class Error(val error: String) : StateSubjectLoaded()
}

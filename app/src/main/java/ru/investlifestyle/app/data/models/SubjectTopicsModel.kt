package ru.investlifestyle.app.data.models

import ru.investlifestyle.app.ui.models.PostUiModel

data class SubjectTopicsModel(
    val subjectTitle: String,
    val postsList: List<PostUiModel>
)

package ru.investlifestyle.app.ui.post

import ru.investlifestyle.app.ui.models.PostUiModel

sealed class LoadPostState {

    object Load : LoadPostState()

    data class Loaded(
        val post: PostUiModel
    ) : LoadPostState()

    data class Error(
        val error:String
    ) : LoadPostState()

    object Empty : LoadPostState()
}
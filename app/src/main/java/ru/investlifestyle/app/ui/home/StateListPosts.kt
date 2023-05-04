package ru.investlifestyle.app.ui.home

import ru.investlifestyle.app.ui.models.PostUiModel
import ru.investlifestyle.app.ui.post.LoadPostState

sealed class StateListPosts {

    object Load : StateListPosts()

    data class Loaded(
        val ListPosts: List<PostUiModel>
    ) : StateListPosts()

    data class Error(
        val error: String
    ) : StateListPosts()

}

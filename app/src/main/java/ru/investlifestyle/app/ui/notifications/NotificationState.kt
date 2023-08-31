package ru.investlifestyle.app.ui.notifications

import ru.investlifestyle.app.ui.models.PostUiModel
import ru.investlifestyle.app.ui.models.UserName

sealed class NotificationState {
    object Load : NotificationState()

    data class LoadedSavePostsAndUserName(
        val postList: List<PostUiModel>,
        val userName: UserName
    ) : NotificationState()

    data class LoadedUserNameOnly(
        val userName: UserName
    ) : NotificationState()

    data class LoadedSavePostsOnly(
        val postList: List<PostUiModel>
    ) : NotificationState()

    object UserNameAndSavePostIsEmpty : NotificationState()
}

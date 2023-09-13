package ru.investlifestyle.app.ui.notifications

import ru.investlifestyle.app.ui.models.PostUiModel
import ru.investlifestyle.app.ui.models.UserNameUi

sealed class NotificationState {
    object Load : NotificationState()

    data class LoadedSavePostsAndUserName(
        val postList: List<PostUiModel>,
        val userName: UserNameUi
    ) : NotificationState()

    data class LoadedUserNameOnly(
        val userName: UserNameUi
    ) : NotificationState()

    data class LoadedSavePostsOnly(
        val postList: List<PostUiModel>
    ) : NotificationState()

    object UserNameAndSavePostIsEmpty : NotificationState()
}

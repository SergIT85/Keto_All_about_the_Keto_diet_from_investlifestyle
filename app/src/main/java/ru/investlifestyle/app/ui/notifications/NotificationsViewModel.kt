package ru.investlifestyle.app.ui.notifications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.investlifestyle.app.domain.usecase.GetAllLikePostsUseCase
import ru.investlifestyle.app.domain.usecase.GetUserNameUseCase
import ru.investlifestyle.app.domain.usecase.LikePostsIsEmptyUseCase
import ru.investlifestyle.app.domain.usecase.SaveUserNameUseCase
import ru.investlifestyle.app.domain.usecase.UserNameIsEmptyUseCase

class NotificationsViewModel @Inject constructor(
    private val getAllLikePostsUseCase: GetAllLikePostsUseCase,
    private val userNameIsEmptyUseCase: UserNameIsEmptyUseCase,
    private val saveUserNameUseCase: SaveUserNameUseCase,
    private val getUserNameUseCase: GetUserNameUseCase,
    private val likePostsIsEmptyUseCase: LikePostsIsEmptyUseCase
) : ViewModel() {

    private var _notificationState = MutableStateFlow<NotificationState>(NotificationState.Load)
    val notificationState = _notificationState.asStateFlow()

    init {
        initialNotificationScreen()
    }
    private fun initialNotificationScreen() {
        viewModelScope.launch {
            if (userNameIsEmptyUseCase.userNameIsEmpty()) {
                if (likePostsIsEmptyUseCase.likePostsIsEmpty()) {
                    _notificationState.value = NotificationState.UserNameAndSavePostIsEmpty
                } else {
                    _notificationState.value = NotificationState.LoadedSavePostsOnly(
                        getAllLikePostsUseCase.getAllLikePosts()
                    )
                }
            } else {
                if (likePostsIsEmptyUseCase.likePostsIsEmpty()) {
                    _notificationState.value = NotificationState.LoadedUserNameOnly(
                        getUserNameUseCase.getUserName()
                    )
                } else {
                    _notificationState.value = NotificationState.LoadedSavePostsAndUserNAme(
                        postList = getAllLikePostsUseCase.getAllLikePosts(),
                        userName = getUserNameUseCase.getUserName()
                    )
                }
            }
        }
    }
}
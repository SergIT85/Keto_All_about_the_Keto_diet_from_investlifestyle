package ru.investlifestyle.app.ui.notifications

import android.annotation.SuppressLint
import android.util.Log
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
import ru.investlifestyle.app.ui.models.UserName

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

    fun saveUserName(userName: String) {
        viewModelScope.launch {
            saveUserNameUseCase.saveUserName(UserName(userName))
            initialNotificationScreen()
        }
    }
    @SuppressLint("LogNotTimber")
    fun initialNotificationScreen() {
        viewModelScope.launch {
            /*_notificationState.value = NotificationState.LoadedSavePostsOnly(
                getAllLikePostsUseCase.getAllLikePosts()
            )*/
            if (userNameIsEmptyUseCase.userNameIsEmpty()) {
                if (!likePostsIsEmptyUseCase.likePostsIsEmpty()) {
                    _notificationState.value = NotificationState.UserNameAndSavePostIsEmpty
                    Log.d("likePostsIsEmpty", "likePostsIsEmpty")
                } else {
                    _notificationState.value = NotificationState.LoadedSavePostsOnly(
                        getAllLikePostsUseCase.getAllLikePosts()
                    )
                    Log.d("likePostsIsEmpty", "likePostsIs__NOT___Empty")
                }
            } else {
                if (likePostsIsEmptyUseCase.likePostsIsEmpty()) {
                    _notificationState.value = NotificationState.LoadedUserNameOnly(
                        getUserNameUseCase.getUserName()
                    )
                } else {
                    _notificationState.value = NotificationState.LoadedSavePostsAndUserName(
                        postList = getAllLikePostsUseCase.getAllLikePosts(),
                        userName = getUserNameUseCase.getUserName()
                    )
                }
            }
        }
    }
}
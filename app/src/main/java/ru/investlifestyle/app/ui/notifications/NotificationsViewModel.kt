package ru.investlifestyle.app.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject
import ru.investlifestyle.app.domain.usecase.GetAllLikePostsUseCase
import ru.investlifestyle.app.domain.usecase.GetUserNameUseCase
import ru.investlifestyle.app.domain.usecase.SaveUserNameUseCase
import ru.investlifestyle.app.domain.usecase.UserNameIsEmptyUseCase
import ru.investlifestyle.app.ui.models.PostUiModel

class NotificationsViewModel @Inject constructor(
    private val getAllLikePostsUseCase: GetAllLikePostsUseCase,
    private val userNameIsEmptyUseCase: UserNameIsEmptyUseCase,
    private val saveUserNameUseCase: SaveUserNameUseCase,
    private val getUserNameUseCase: GetUserNameUseCase
) : ViewModel() {

    private var _loadLikePosts = MutableLiveData<List<PostUiModel>>()
    val loadLikePosts: LiveData<List<PostUiModel>>
        get() = _loadLikePosts
}
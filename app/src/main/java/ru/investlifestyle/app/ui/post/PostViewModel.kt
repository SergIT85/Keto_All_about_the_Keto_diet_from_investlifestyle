package ru.investlifestyle.app.ui.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import ru.investlifestyle.app.domain.usecase.LoadOnePostUseCase
import ru.investlifestyle.app.ui.models.PostUiModel
import java.io.IOException
import javax.inject.Inject

class PostViewModel @Inject constructor(
    private val loadOnePostUseCase: LoadOnePostUseCase
) : ViewModel() {



    private var _onePostViewModel = MutableStateFlow<LoadPostState>(LoadPostState.Empty)
    val onePostViewModel = _onePostViewModel.asStateFlow()

    fun getPostById(postId: Int) {

        viewModelScope.launch {
            _onePostViewModel.value = LoadPostState.Load

            try {
                _onePostViewModel.value =
                LoadPostState.Loaded(loadOnePostUseCase.loadOnePost(postId))

            } catch (exception: IOException) {
                _onePostViewModel.value = LoadPostState.Error(exception.message.toString())
            } catch (exception: HttpException) {
                _onePostViewModel.value = LoadPostState.Error(exception.message.toString())
            }
        }
    }
}
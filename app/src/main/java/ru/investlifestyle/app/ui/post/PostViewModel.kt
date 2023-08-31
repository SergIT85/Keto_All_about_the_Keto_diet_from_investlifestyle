package ru.investlifestyle.app.ui.post

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import java.io.IOException
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.HttpException
import ru.investlifestyle.app.domain.usecase.*

class PostViewModel @Inject constructor(
    private val loadOnePostUseCase: LoadOnePostUseCase,
    private val insertLikePostUseCase: InsertLikePostUseCase,
    private val deleteLikePostByIdUseCase: DeleteLikePostByIdUseCase,
    private val getLikePostByIdBooleanUseCase: GetLikePostByIdBooleanUseCase,
) : ViewModel() {

    private var _onePostViewModel = MutableStateFlow<LoadPostState>(LoadPostState.Empty)
    val onePostViewModel = _onePostViewModel.asStateFlow()

    private var _likedPosts = MutableLiveData<Boolean>()
    val likedPosts: LiveData<Boolean>
        get() = _likedPosts

    @SuppressLint("LogNotTimber")
    fun getPostById(postId: Int) {

        viewModelScope.launch {
            _onePostViewModel.value = LoadPostState.Load
            try {
                _onePostViewModel.value =
                    LoadPostState.Loaded(loadOnePostUseCase.loadOnePost(postId))
                val boolean = getLikePostByIdBooleanUseCase.getLikePostByIdBoolean(postId)
                _likedPosts.value = boolean
                Log.d("PostViewModel", "$boolean")
            } catch (exception: IOException) {
                _onePostViewModel.value = LoadPostState.Error(exception.message.toString())
            } catch (exception: HttpException) {
                _onePostViewModel.value = LoadPostState.Error(exception.message.toString())
            }
        }
    }

    fun insertLikePost() {
        viewModelScope.launch {
            onePostViewModel.collect {
                when (it) {
                    is LoadPostState.Loaded -> {
                        it.post.liked = true
                        _likedPosts.value = true
                        insertLikePostUseCase.insertLikePost(it.post)
                    }
                    else -> {}
                }
            }
        }
    }

    fun deleteLikePostById() {
        viewModelScope.launch {
            onePostViewModel.collect {
                when (it) {
                    is LoadPostState.Loaded -> {
                        it.post.liked = false
                        _likedPosts.value = false
                        deleteLikePostByIdUseCase.deleteLikePostById(it.post.id)
                    }
                    else -> {}
                }
            }
        }
    }
}
package ru.investlifestyle.app.ui.theme

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import javax.inject.Inject
import kotlinx.coroutines.flow.*
import ru.investlifestyle.app.domain.usecase.GetPostPagingSourceUseCase
import ru.investlifestyle.app.domain.usecase.LoadSubjectPostsUseCase
import ru.investlifestyle.app.ui.home.StateListPosts
import ru.investlifestyle.app.ui.models.PostUiModel

class ThemeViewModel @Inject constructor(
    private val getPostPagingSourceUseCase: GetPostPagingSourceUseCase,
    private val loadSubjectPostsUseCase: LoadSubjectPostsUseCase,
) : ViewModel() {
    private var _postPagingSourceVm = MutableStateFlow<StateListPosts>(StateListPosts.Load)
    val postPagingSourceVm = _postPagingSourceVm.asStateFlow()

    private var _postLiveData = MutableLiveData<List<PostUiModel>>()
    val postLiveData: LiveData<List<PostUiModel>>
        get() = _postLiveData

    suspend fun getPostLivedataByCategoryId(categoryId: Int) {
        _postLiveData.value = loadSubjectPostsUseCase.loadSubjectPosts(categoryId)
    }

    fun getPostPagingSourceByCategoryId(categoryId: Int) {
        _postPagingSourceVm.flatMapLatest {
            getPostPagingSourceUseCase.getPostPagingSource(categoryId)
                .cachedIn(viewModelScope)
        }
    }
    // Дальше надо сделать открытие фрагмента темы из фрагмента списка тем, из него уже открывать
    // метод getPostPagingSourceByCategoryId. После подписываться на postPagingSourceVm
    // и отображать список
}
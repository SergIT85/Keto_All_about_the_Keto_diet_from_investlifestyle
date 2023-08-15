package ru.investlifestyle.app.ui.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import javax.inject.Inject
import kotlinx.coroutines.flow.*
import ru.investlifestyle.app.domain.usecase.GetPostPagingSourceUseCase
import ru.investlifestyle.app.ui.home.StateListPosts

class ThemeViewModel @Inject constructor(
    private val getPostPagingSourceUseCase: GetPostPagingSourceUseCase
) : ViewModel() {
    private var _postPagingSourceVm = MutableStateFlow<StateListPosts>(StateListPosts.Load)
    val postPagingSourceVm = _postPagingSourceVm.asStateFlow()

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
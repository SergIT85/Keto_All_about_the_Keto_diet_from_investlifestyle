package ru.investlifestyle.app.ui.theme

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import javax.inject.Inject
import kotlinx.coroutines.flow.*
import ru.investlifestyle.app.domain.usecase.GetPostPagingSourceUseCase

class ThemeViewModel @Inject constructor(
    private val getPostPagingSourceUseCase: GetPostPagingSourceUseCase
) : ViewModel() {

    private var _postsPagingData = MutableLiveData(0)
    fun postsPagingDataCategory(categoryId: Int, categoryType: String) = _postsPagingData.asFlow()
        .flatMapLatest {
            getPostPagingSourceUseCase
                .getPostPagingSource(categoryId, categoryType)
                .cachedIn(viewModelScope)
        }
}

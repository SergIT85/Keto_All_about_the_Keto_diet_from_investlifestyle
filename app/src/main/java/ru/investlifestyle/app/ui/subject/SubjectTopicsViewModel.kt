package ru.investlifestyle.app.ui.subject

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.investlifestyle.app.data.networkApi.PostsModelDataItem
import ru.investlifestyle.app.domain.usecase.GetMainPostsListUseCase
import javax.inject.Inject

@SuppressLint("CheckResult")
class SubjectTopicsViewModel @Inject constructor(
    private val getMainPostsListUseCase: GetMainPostsListUseCase
) : ViewModel()  {

    var postListViewModel: MutableLiveData<List<PostsModelDataItem>> = MutableLiveData()

    init {
        viewModelScope.launch{
            getPostsApi()
        }


    }

    private val _text = MutableLiveData<String>().apply {
        value = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
    }
    val text: LiveData<String> = _text
    @SuppressLint("LogNotTimber")
    suspend fun getPostsApi():List<PostsModelDataItem> {
        //val result = getPostsListUseCase.getPostsList(1)
        //Log.d("TopicsViewModel","${result.subscribe { it -> toString() }}")
        return getMainPostsListUseCase.getMainPostsList(1)

    }
}
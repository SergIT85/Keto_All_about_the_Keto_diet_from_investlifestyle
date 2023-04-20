package ru.investlifestyle.app.ui.dashboard

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.investlifestyle.app.data.repository.PostsRepositoryImpl
import ru.investlifestyle.app.domain.usecase.GetMainPostsListUseCase
import ru.investlifestyle.app.utils.PostsModelDataItem
import javax.inject.Inject

@SuppressLint("CheckResult")
class TopicsViewModel @Inject constructor(
    private val getMainPostsListUseCase: GetMainPostsListUseCase
) : ViewModel()  {





    var postListViewModel: MutableLiveData<List<PostsModelDataItem>> = MutableLiveData()

    init {
        getPostsApi()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { it ->
                postListViewModel.value = it
            }


    }

    private val _text = MutableLiveData<String>().apply {
        value = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
    }
    val text: LiveData<String> = _text
    @SuppressLint("LogNotTimber")
    fun getPostsApi():Single<List<PostsModelDataItem>> {
        //val result = getPostsListUseCase.getPostsList(1)
        //Log.d("TopicsViewModel","${result.subscribe { it -> toString() }}")
        return getMainPostsListUseCase.getMainPostsList(1)

    }
}
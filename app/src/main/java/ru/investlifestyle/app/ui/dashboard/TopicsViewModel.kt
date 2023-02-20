package ru.investlifestyle.app.ui.dashboard

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.investlifestyle.app.data.dto.PostApi
import ru.investlifestyle.app.data.networkApi.PostsApiInterface
import ru.investlifestyle.app.data.networkApi.examin.ApiClient
import ru.investlifestyle.app.data.networkApi.examin.Repo
import ru.investlifestyle.app.data.repository.PostsRepository
import ru.investlifestyle.app.utils.PostsModelData
import ru.investlifestyle.app.utils.PostsModelDataItem
import javax.inject.Inject

@SuppressLint("CheckResult")
class TopicsViewModel(private val repository: Repo): ViewModel()  {



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
    fun getPostsApi():Single<List<PostsModelDataItem>> {
        return repository.getPost(1)
    }
}
package ru.investlifestyle.app.ui.home

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.investlifestyle.app.domain.usecase.LoadPostsUseCase
import ru.investlifestyle.app.ui.models.PostUiModel
import javax.inject.Inject

@SuppressLint("CheckResult")
class HomeViewModel @Inject constructor(
    private val loadPostsUseCase: LoadPostsUseCase
) : ViewModel() {

    private var _postsListViewModel: MutableLiveData<List<PostUiModel>> = MutableLiveData()
    val postsListViewModel: LiveData<List<PostUiModel>>
        get() = _postsListViewModel

    init {
        getPostList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { it ->
                _postsListViewModel.value = it

            }
    }

    fun getPostList(): Single<List<PostUiModel>> {
        return loadPostsUseCase.getMainPostList(10)
    }

}
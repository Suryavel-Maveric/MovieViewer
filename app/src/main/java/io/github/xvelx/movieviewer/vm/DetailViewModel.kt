package io.github.xvelx.movieviewer.vm

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.xvelx.movieviewer.network.MvApi
import io.github.xvelx.movieviewer.network.dto.TitleDetail
import io.reactivex.android.schedulers.AndroidSchedulers

class DetailViewModel @ViewModelInject constructor(private val mvApi: MvApi) : ViewModel() {

    private val detailLiveData = MutableLiveData<TitleDetail>()

    fun getTitleDetail(titleId: String): LiveData<TitleDetail> {
        mvApi.getTitle(titleId).observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                detailLiveData.value = it
            }, {
                println(it.localizedMessage)
            })
        return detailLiveData
    }
}
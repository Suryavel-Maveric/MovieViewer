package io.github.xvelx.movieviewer.network.ds

import androidx.paging.PageKeyedDataSource
import io.github.xvelx.movieviewer.network.MvApi
import io.github.xvelx.movieviewer.network.dto.SearchItem
import io.reactivex.android.schedulers.AndroidSchedulers

class SearchDataSource(
    private val query: String,
    private val titleType: String,
    private val mvApi: MvApi
) :
    PageKeyedDataSource<Int, SearchItem>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, SearchItem>
    ) {

        mvApi.searchTitle(query, titleType, pageNo = 1)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                callback.onResult(it.searchItems, null, 2)
            }, {
                // TODO
                println("Error Fetching --- ${it.localizedMessage}")
            })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, SearchItem>) {
        mvApi.searchTitle(query, titleType, pageNo = params.key)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                callback.onResult(it.searchItems, params.key + 1)
            }, {
                // TODO
                println("Error Fetching --- ${it.localizedMessage}")
            })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, SearchItem>) = Unit
}
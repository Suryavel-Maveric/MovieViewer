package io.github.xvelx.movieviewer.vm

import androidx.annotation.WorkerThread
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import io.github.xvelx.movieviewer.network.MvApi
import io.github.xvelx.movieviewer.network.ds.SearchDataSourceFactory
import io.github.xvelx.movieviewer.network.dto.SearchItem
import io.github.xvelx.movieviewer.network.dto.SearchResult
import io.github.xvelx.movieviewer.util.LoadingState
import io.github.xvelx.movieviewer.util.State

class SearchViewModel @ViewModelInject constructor(
    private val mvApi: MvApi
) : BaseViewModel() {

    private var itemPagedList: LiveData<PagedList<SearchItem>>? = null
    private var persistedQuery: String? = null
    private var persistedType: String? = null

    // Backing Field
    private val _searchStateNotifier = MutableLiveData<LoadingState>()

    val searchStateNotifier
        get() = _searchStateNotifier

    fun getSearchItemPagedList(query: String, titleType: String): LiveData<PagedList<SearchItem>>? {
        if (shouldIgnoreNewSearch(query, titleType)) return itemPagedList

        persistedQuery = query
        persistedType = titleType

        val dataSourceFactory = SearchDataSourceFactory(this)
        val pagedListConfig =
            PagedList.Config.Builder().setEnablePlaceholders(false).setPageSize(1).build()
        itemPagedList = LivePagedListBuilder(
            dataSourceFactory,
            pagedListConfig
        ).build()
        return itemPagedList!!
    }

    private fun shouldIgnoreNewSearch(query: String, titleType: String) =
        (query == persistedQuery && persistedType == titleType) || query.isEmpty()

    @WorkerThread
    fun searchTitle(pageNo: Int = 1, onNewSearchResult: (SearchResult) -> Unit) {
        searchStateNotifier.postValue(LoadingState(State.LOADING))
        mvApi.searchTitle(persistedQuery!!, persistedType!!, pageNo)
            .execute().body()?.let(onNewSearchResult)
        searchStateNotifier.postValue(LoadingState(State.LOADED))
    }


}
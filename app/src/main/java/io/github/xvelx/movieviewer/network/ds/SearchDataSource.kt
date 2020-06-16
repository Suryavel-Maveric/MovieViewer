package io.github.xvelx.movieviewer.network.ds

import androidx.paging.PageKeyedDataSource
import io.github.xvelx.movieviewer.network.dto.SearchItem
import io.github.xvelx.movieviewer.vm.SearchViewModel

/**
 * Paged DataSource to fetch title list on-demand.
 */
class SearchDataSource(
    private val searchViewModel: SearchViewModel
) :
    PageKeyedDataSource<Int, SearchItem>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, SearchItem>
    ) {

        searchViewModel.searchTitle {
            callback.onResult(it.searchItems ?: emptyList(), null, 2)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, SearchItem>) {
        searchViewModel.searchTitle(pageNo = params.key) {
            callback.onResult(it.searchItems ?: emptyList(), params.key + 1)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, SearchItem>) = Unit
}
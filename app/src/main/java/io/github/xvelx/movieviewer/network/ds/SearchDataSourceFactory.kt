package io.github.xvelx.movieviewer.network.ds

import androidx.paging.DataSource
import io.github.xvelx.movieviewer.network.dto.SearchItem
import io.github.xvelx.movieviewer.vm.SearchViewModel

class SearchDataSourceFactory(
    private val searchViewModel: SearchViewModel
) :
    DataSource.Factory<Int, SearchItem>() {

    override fun create(): DataSource<Int, SearchItem> =
        SearchDataSource(searchViewModel)
}
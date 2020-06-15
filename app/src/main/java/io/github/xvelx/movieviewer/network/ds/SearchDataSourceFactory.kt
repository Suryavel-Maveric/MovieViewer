package io.github.xvelx.movieviewer.network.ds

import androidx.paging.DataSource
import io.github.xvelx.movieviewer.network.MvApi
import io.github.xvelx.movieviewer.network.dto.SearchItem

class SearchDataSourceFactory(val query: String, val mvApi: MvApi) :
    DataSource.Factory<Int, SearchItem>() {

    override fun create(): DataSource<Int, SearchItem> =
        SearchDataSource(query, mvApi)
}
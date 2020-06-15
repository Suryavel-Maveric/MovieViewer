package io.github.xvelx.movieviewer.activities.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import io.github.xvelx.movieviewer.R
import io.github.xvelx.movieviewer.network.dto.SearchItem

class SearchListAdapter : PagedListAdapter<SearchItem, SearchViewHolder>(
    SearchItem.DIFF
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder =
        SearchViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.view_search_item,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }
}
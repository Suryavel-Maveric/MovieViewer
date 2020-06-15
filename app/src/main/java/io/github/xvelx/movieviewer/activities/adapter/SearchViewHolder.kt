package io.github.xvelx.movieviewer.activities.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import io.github.xvelx.movieviewer.network.dto.SearchItem
import kotlinx.android.synthetic.main.view_search_item.view.*

class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: SearchItem) {
        itemView.titleIdView.setText(item.title)
    }
}
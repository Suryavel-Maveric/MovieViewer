package io.github.xvelx.movieviewer.activities.adapter

import android.view.View
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.github.xvelx.movieviewer.R
import io.github.xvelx.movieviewer.network.dto.SearchItem
import kotlinx.android.synthetic.main.view_search_item.view.*

class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: SearchItem) {
        itemView.titleIdView.setText(item.title)
        Glide.with(itemView).load(item.posterUrl)
            .placeholder(R.drawable.place_holder_poster)
            .into(itemView.posterView)

        itemView.setOnClickListener {
            itemView.findNavController().navigate(R.id.detailFragment)
        }
    }
}
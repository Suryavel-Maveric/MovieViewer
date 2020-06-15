package io.github.xvelx.movieviewer.activities.adapter

import android.view.View
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import io.github.xvelx.movieviewer.R
import io.github.xvelx.movieviewer.databinding.ViewSearchItemBinding
import io.github.xvelx.movieviewer.fragments.DetailFragment
import io.github.xvelx.movieviewer.network.dto.SearchItem

class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: SearchItem) {
        DataBindingUtil.bind<ViewSearchItemBinding>(itemView)?.searchItem = item

        itemView.setOnClickListener {
            itemView.findNavController().navigate(
                R.id.detailFragment,
                bundleOf(DetailFragment.ARG_TITLE_ID to item.titleId)
            )
        }
    }
}
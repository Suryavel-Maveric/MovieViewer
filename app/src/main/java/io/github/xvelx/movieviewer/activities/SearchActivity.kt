package io.github.xvelx.movieviewer.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import io.github.xvelx.movieviewer.R
import io.github.xvelx.movieviewer.activities.adapter.SearchListAdapter
import io.github.xvelx.movieviewer.network.dto.SearchItem
import io.github.xvelx.movieviewer.vm.SearchViewModel
import kotlinx.android.synthetic.main.activity_search.*

@AndroidEntryPoint
class SearchActivity : BaseActivity() {

    var searchPagedLiveData: LiveData<PagedList<SearchItem>>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val searchListAdapter = SearchListAdapter()

        recyclerView.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        recyclerView.adapter = searchListAdapter

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val searchViewModel =
                    ViewModelProvider(this@SearchActivity).get(SearchViewModel::class.java)

                // New Live Data created for every search query
                // So removing the registered observers
                searchPagedLiveData?.removeObservers(this@SearchActivity)
                searchPagedLiveData = searchViewModel.getSearchItemPagedList(query ?: "")
                searchPagedLiveData?.observe(this@SearchActivity, Observer {
                    searchListAdapter.submitList(it)
                })
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean = true
        })
    }
}


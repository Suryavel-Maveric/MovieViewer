package io.github.xvelx.movieviewer.activities

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
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
    var searchQueryText: String? = null
    var searchTitleType: String? = null

    lateinit var searchListAdapter: SearchListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        searchListAdapter = SearchListAdapter()

        recyclerView.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        recyclerView.adapter = searchListAdapter

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchQueryText = query
                searchTitle(
                    searchQueryText,
                    searchTitleType
                )
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean = true
        })

        searchType.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            android.R.id.text1,
            SearchType.values()
        )

        searchType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) = Unit

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                searchTitleType = (searchType.adapter.getItem(position) as? SearchType)?.searchText
                searchTitle(
                    searchQueryText,
                    searchTitleType
                )
            }
        }
    }

    fun searchTitle(query: String?, type: String?) {
        query?.let {
            val searchViewModel =
                ViewModelProvider(this@SearchActivity).get(SearchViewModel::class.java)

            // New Live Data created for every search query
            // So removing the registered observers
            searchPagedLiveData?.removeObservers(this@SearchActivity)
            searchPagedLiveData =
                searchViewModel.getSearchItemPagedList(it, type ?: "")
            searchPagedLiveData?.observe(this@SearchActivity, Observer {
                searchListAdapter.submitList(it)
            })
        }
    }
}



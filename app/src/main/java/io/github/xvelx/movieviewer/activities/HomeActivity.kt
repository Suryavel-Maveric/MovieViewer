package io.github.xvelx.movieviewer.activities

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import butterknife.OnItemSelected
import dagger.hilt.android.AndroidEntryPoint
import io.github.xvelx.movieviewer.R
import io.github.xvelx.movieviewer.activities.adapter.SearchListAdapter
import io.github.xvelx.movieviewer.network.dto.SearchItem
import io.github.xvelx.movieviewer.vm.SearchViewModel
import kotlinx.android.synthetic.main.activity_search.*

@AndroidEntryPoint
class HomeActivity : BaseActivity() {

    private var searchPagedLiveData: LiveData<PagedList<SearchItem>>? = null
    private var searchQueryText: String? = null
    private var searchTitleType: String? = null
    private lateinit var searchListAdapter: SearchListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        searchListAdapter = SearchListAdapter()

        recyclerView.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        recyclerView.adapter = searchListAdapter

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                onSearchQueryEntered(query)
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
    }

    fun onSearchQueryEntered(query: String?) {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(searchView.windowToken, 0)
        searchQueryText = query
        searchTitle(
            searchQueryText,
            searchTitleType
        )
    }

    @OnItemSelected(R.id.searchType)
    fun onTitleTypeSelected(position: Int) {
        searchTitleType = (searchType.adapter.getItem(position) as? SearchType)?.searchText
        searchTitle(
            searchQueryText,
            searchTitleType
        )
    }

    fun searchTitle(query: String?, type: String?) {
        query?.let {
            val searchViewModel =
                ViewModelProvider(this@HomeActivity).get(SearchViewModel::class.java)

            // New Live Data created for every search query
            // So removing the registered observers
            searchPagedLiveData?.removeObservers(this@HomeActivity)
            searchPagedLiveData =
                searchViewModel.getSearchItemPagedList(it, type ?: "")
            searchPagedLiveData?.observe(this@HomeActivity, Observer {
                searchListAdapter.submitList(it)
            })
        }
    }
}



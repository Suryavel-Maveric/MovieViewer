package io.github.xvelx.movieviewer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import butterknife.OnItemSelected
import dagger.hilt.android.AndroidEntryPoint
import io.github.xvelx.movieviewer.R
import io.github.xvelx.movieviewer.activities.SearchType
import io.github.xvelx.movieviewer.activities.adapter.SearchListAdapter
import io.github.xvelx.movieviewer.network.dto.SearchItem
import io.github.xvelx.movieviewer.util.hideKeyboard
import io.github.xvelx.movieviewer.vm.SearchViewModel
import kotlinx.android.synthetic.main.fragment_search.*

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var searchPagedLiveData: LiveData<PagedList<SearchItem>>? = null
    private var searchQueryText: String? = null
    private var searchTitleType: String? = null
    private lateinit var searchListAdapter: SearchListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchListAdapter = SearchListAdapter()

        recyclerView.layoutManager =
            GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        recyclerView.adapter = searchListAdapter

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                onSearchQueryEntered(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean = true
        })

        context?.let { ctxt ->
            searchType.adapter = ArrayAdapter(
                ctxt,
                android.R.layout.simple_spinner_dropdown_item,
                android.R.id.text1,
                SearchType.values()
            )
        }
    }

    fun onSearchQueryEntered(query: String?) {
        searchView.hideKeyboard()
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
                ViewModelProvider(this).get(SearchViewModel::class.java)

            // New Live Data created for every search query
            // So removing the registered observers
            searchPagedLiveData?.removeObservers(this)
            searchPagedLiveData =
                searchViewModel.getSearchItemPagedList(it, type ?: "")
            searchPagedLiveData?.observe(this, Observer {
                searchListAdapter.submitList(it)
            })
        }
    }
}
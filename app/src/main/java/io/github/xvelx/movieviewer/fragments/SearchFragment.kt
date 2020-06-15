package io.github.xvelx.movieviewer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import io.github.xvelx.movieviewer.activities.SearchType
import io.github.xvelx.movieviewer.activities.adapter.SearchListAdapter
import io.github.xvelx.movieviewer.network.dto.SearchItem
import io.github.xvelx.movieviewer.util.hideKeyboard
import io.github.xvelx.movieviewer.vm.SearchViewModel
import kotlinx.android.synthetic.main.fragment_search.*

@AndroidEntryPoint
class SearchFragment : BaseFragment() {

    private var searchPagedLiveData: LiveData<PagedList<SearchItem>>? = null
    private lateinit var searchListAdapter: SearchListAdapter

    override val screenTitle
        get() = getString(R.string.search_page_title)

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

        configureSearchView()

        configureSearchTypeSlider()
    }

    private fun configureSearchView() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchTitle()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean = true
        })
    }

    private fun configureSearchTypeSlider() {
        context?.let { ctxt ->
            searchType.adapter = ArrayAdapter(
                ctxt,
                android.R.layout.simple_spinner_dropdown_item,
                android.R.id.text1,
                SearchType.values()
            )
        }
        configureSliderListener()
    }

    fun configureSliderListener() {
        searchType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) = Unit

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                searchTitle()
            }
        }
    }

    fun searchTitle() {
        // Hide keyboard if it is opened
        searchView.hideKeyboard()

        val query = searchView.query
        val titleType =
            (searchType.adapter.getItem(searchType.selectedItemPosition) as? SearchType)?.searchText
        val searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)

        // New Live Data created for every search query
        // So removing the registered observers
        searchPagedLiveData?.removeObservers(this)
        searchPagedLiveData =
            searchViewModel.getSearchItemPagedList(query.toString(), titleType ?: "")
        searchPagedLiveData?.observe(this, Observer {
            searchListAdapter.submitList(it)
        })
    }
}
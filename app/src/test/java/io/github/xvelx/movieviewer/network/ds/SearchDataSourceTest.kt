package io.github.xvelx.movieviewer.network.ds

import androidx.paging.PageKeyedDataSource
import io.github.xvelx.movieviewer.common.BaseTest
import io.github.xvelx.movieviewer.network.dto.SearchItem
import io.github.xvelx.movieviewer.network.dto.SearchResult
import io.github.xvelx.movieviewer.vm.SearchViewModel
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Test

class SearchDataSourceTest : BaseTest() {

    @RelaxedMockK
    lateinit var initialCallback: PageKeyedDataSource.LoadInitialCallback<Int, SearchItem>

    @RelaxedMockK
    lateinit var loadCallback: PageKeyedDataSource.LoadCallback<Int, SearchItem>

    @MockK
    lateinit var searchViewModel: SearchViewModel

    @InjectMockKs
    lateinit var searchDataSource: SearchDataSource

    @MockK
    lateinit var searchResult: SearchResult

    val resultSlot = slot<(SearchResult) -> Unit>()

    @Test
    fun loadInitial_whenSearchItemPresents_invokesCallBackWithSame() {
        val searchItems = listOf(mockk<SearchItem>())

        every { searchResult.searchItems } returns searchItems

        every {
            searchViewModel.searchTitle(onNewSearchResult = capture(resultSlot))
        } answers {
            resultSlot.captured.invoke(searchResult)
        }

        searchDataSource.loadInitial(mockk(), initialCallback)

        verify { initialCallback.onResult(searchItems, null, 2) }
    }

    @Test
    fun loadInitial_whenSearchItemNull_invokesWithEmptyList() {

        every { searchResult.searchItems } returns null

        every {
            searchViewModel.searchTitle(onNewSearchResult = capture(resultSlot))
        } answers {
            resultSlot.captured.invoke(searchResult)
        }

        searchDataSource.loadInitial(mockk(), initialCallback)

        verify { initialCallback.onResult(emptyList(), null, 2) }
    }

    @Test
    fun loadAfter_whenSearchItemPresents_invokesCallBackWithSame() {
        val params = PageKeyedDataSource.LoadParams<Int>(1, 1)
        val searchItems = listOf(mockk<SearchItem>())

        every { searchResult.searchItems } returns searchItems

        every {
            searchViewModel.searchTitle(onNewSearchResult = capture(resultSlot))
        } answers {
            resultSlot.captured.invoke(searchResult)
        }

        searchDataSource.loadAfter(params, loadCallback)

        verify { loadCallback.onResult(searchItems, params.key + 1) }
    }

    @Test
    fun loadAfter_whenSearchItemNull_invokesWithEmptyList() {
        val params = PageKeyedDataSource.LoadParams<Int>(1, 1)

        every { searchResult.searchItems } returns null

        every {
            searchViewModel.searchTitle(onNewSearchResult = capture(resultSlot))
        } answers {
            resultSlot.captured.invoke(searchResult)
        }

        searchDataSource.loadAfter(params, loadCallback)

        verify { loadCallback.onResult(emptyList(), params.key + 1) }
    }
}



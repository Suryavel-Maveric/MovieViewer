package io.github.xvelx.movieviewer.vm

import io.github.xvelx.movieviewer.common.BaseTest
import io.github.xvelx.movieviewer.network.MvApi
import io.github.xvelx.movieviewer.network.dto.SearchResult
import io.github.xvelx.movieviewer.util.State
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import retrofit2.Call
import retrofit2.Response

class SearchViewModelTest : BaseTest() {

    private companion object {
        const val SEARCH_QUERY1 = "Marvel"
        const val SEARCH_QUERY2 = "DC"

        const val SEARCH_TYPE1 = "Movies"
        const val SEARCH_TYPE2 = "Series"
    }

    @MockK
    lateinit var mvApi: MvApi

    @InjectMockKs
    lateinit var searchViewModel: SearchViewModel


    @Test
    fun getSearchItemPagedList_whenEmptyQuery_returnsNull() {

        assertThat(searchViewModel.getSearchItemPagedList("", "")).isNull()
    }

    @Test
    fun getSearchItemPagedList_whenSameQueryAndTypePassed_returnsSamePagedList() {
        val pagedList = searchViewModel.getSearchItemPagedList(SEARCH_QUERY1, SEARCH_TYPE1)

        assertThat(searchViewModel.getSearchItemPagedList(SEARCH_QUERY1, SEARCH_TYPE1))
            .isEqualTo(pagedList)
    }

    @Test
    fun getSearchItemPagedList_whenNewQueryPassed_returnsNewPagedList() {
        val pagedList = searchViewModel.getSearchItemPagedList(SEARCH_QUERY1, SEARCH_TYPE1)

        assertThat(searchViewModel.getSearchItemPagedList(SEARCH_QUERY2, SEARCH_TYPE1))
            .isNotEqualTo(pagedList)
    }

    @Test
    fun getSearchItemPagedList_whenNewTypePassed_returnsNewPagedList() {
        val pagedList = searchViewModel.getSearchItemPagedList(SEARCH_QUERY2, SEARCH_TYPE1)

        assertThat(searchViewModel.getSearchItemPagedList(SEARCH_QUERY2, SEARCH_TYPE2))
            .isNotEqualTo(pagedList)
    }

    // searchTitle
    @Test
    fun searchTitle_whenLoaded_hasLoadedStateValue() {
        mockSearchApi {
            val searchResult = mockk<SearchResult>()
            every { body() } returns searchResult
            every { searchResult.response } returns true
        }

        searchViewModel.getSearchItemPagedList(SEARCH_QUERY1, SEARCH_TYPE1)

        searchViewModel.searchTitle { }

        assertThat(searchViewModel.searchStateNotifier.value?.state).isEqualTo(State.LOADED)
    }

    @Test
    fun searchTitle_whenResponseIsNull_hasErrorStateValue() {
        mockSearchApi {
            every { body() } returns null
        }

        searchViewModel.getSearchItemPagedList(SEARCH_QUERY1, SEARCH_TYPE1)

        searchViewModel.searchTitle { }

        assertThat(searchViewModel.searchStateNotifier.value?.state).isEqualTo(State.ERROR)
    }

    @Test
    fun searchTitle_whenNoRecord_hasNoRecordStateValue() {
        mockSearchApi {
            val searchResult = mockk<SearchResult>()
            every { body() } returns searchResult
            every { searchResult.response } returns false
        }

        searchViewModel.getSearchItemPagedList(SEARCH_QUERY1, SEARCH_TYPE1)

        searchViewModel.searchTitle { }

        assertThat(searchViewModel.searchStateNotifier.value?.state).isEqualTo(State.NO_RECORD_FOUND)
    }

    private fun mockSearchApi(configureResult: Response<SearchResult>.() -> Unit) {
        val call = mockk<Call<SearchResult>>()
        val response = mockk<Response<SearchResult>>()

        every { mvApi.searchTitle(any(), any(), any()) } returns call
        every { call.execute() } returns response

        configureResult(response)
    }
}
package io.github.xvelx.movieviewer.network.dto

import io.github.xvelx.movieviewer.common.BaseTest
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class SearchItemTest : BaseTest() {

    @Test
    fun equals_whenDifferentObject_returnsFalse() {
        assertThat(mockk<SearchItem>() == mockk()).isFalse()
    }

    @Test
    fun equals_whenSameObject_returnsTrue() {
        val searchItem = mockk<SearchItem>()
        assertThat(searchItem == searchItem).isTrue()
    }

    @Test
    fun equals_whenTitleIdSame_returnsTrue() {
        val titleId = "t123"
        val searchItem = SearchItem(null, null, titleId, null, null)
        val searchItem2 = SearchItem(null, null, titleId, null, null)

        assertThat(searchItem == searchItem2).isTrue()
    }
}
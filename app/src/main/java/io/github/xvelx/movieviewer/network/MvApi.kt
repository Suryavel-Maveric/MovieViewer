package io.github.xvelx.movieviewer.network

import io.github.xvelx.movieviewer.network.dto.SearchResult
import io.github.xvelx.movieviewer.network.dto.TitleDetail
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MvApi {

    private companion object {
        const val API_KEY = "b9bd48a6"
    }

    @GET("/?apikey=$API_KEY")
    fun searchTitle(
        @Query("s") search: String,
        @Query("type") type: String = "",
        @Query("page") pageNo: Int = 1
    ): Single<SearchResult>

    @GET("/?apikey=$API_KEY")
    fun getTitle(@Query("i") titleId: String): Single<TitleDetail>
}
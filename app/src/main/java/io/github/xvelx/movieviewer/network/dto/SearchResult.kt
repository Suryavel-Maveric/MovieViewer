package io.github.xvelx.movieviewer.network.dto

import androidx.recyclerview.widget.DiffUtil
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

data class SearchItem(
    @JsonProperty("Title") val title: String?,
    @JsonProperty("Year") val year: String?,
    @JsonProperty("imdbID") val titleId: String?,
    @JsonProperty("Type") val titleType: String?,
    @JsonProperty("Poster") val posterUrl: String?
) {
    companion object {
        val DIFF = object : DiffUtil.ItemCallback<SearchItem>() {

            override fun areItemsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean =
                oldItem.titleId.equals(newItem.titleId)

            override fun areContentsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean =
                oldItem.titleId.equals(newItem.titleId)
        }
    }

    override fun equals(other: Any?): Boolean {
        return other === this || (other is SearchItem && other.titleId == titleId)
    }

    override fun hashCode(): Int = Objects.hashCode(this)
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class SearchResult(
    @JsonProperty("Search") val searchItems: List<SearchItem?>?,
    @JsonProperty("totalResults") val totalResults: Int?,
    @JsonProperty("Response") val response: Boolean,
    @JsonProperty("Error") val error: String?
)
package io.github.xvelx.movieviewer.activities

/**
 * Represent Title Types. Used for search criteria.
 */
enum class SearchType(val searchText: String) {
    ALL(""),
    MOVIE("movie"),
    SERIES("series"),
    EPISODE("episode");
}
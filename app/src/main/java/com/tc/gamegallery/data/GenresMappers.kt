package com.tc.gamegallery.data

import com.tc.gamegallery.GameGenresQuery
import com.tc.gamegallery.domain.GenresTags

fun GameGenresQuery.AllGenres.toGenres(): GenresTags {
    return GenresTags(
        nextPage = nextPage,
        results = results?.mapNotNull {it.toResult()} ?: emptyList(),
    )
}
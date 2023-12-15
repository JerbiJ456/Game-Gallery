package com.tc.gamegallery.data

import com.tc.gamegallery.GameTagsQuery
import com.tc.gamegallery.domain.GenresTags

fun GameTagsQuery.AllTags.toTags(): GenresTags {
    return GenresTags(
        nextPage = nextPage,
        results = results?.mapNotNull {it.toResult()} ?: emptyList(),
    )
}
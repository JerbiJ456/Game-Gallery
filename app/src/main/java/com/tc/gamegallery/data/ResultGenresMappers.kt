package com.tc.gamegallery.data

import com.tc.gamegallery.GameGenresQuery
import com.tc.gamegallery.domain.ResultGenresTags

fun GameGenresQuery.Result.toResult(): ResultGenresTags {
    return ResultGenresTags(
        id = id ?: 1,
        name = name ?: "No Name",
        thumbnailImage = thumbnailImage ?: "",
        games = games?.mapNotNull {it.toGames()} ?: emptyList()
    )
}
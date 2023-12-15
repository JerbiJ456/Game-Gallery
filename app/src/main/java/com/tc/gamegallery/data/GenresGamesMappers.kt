package com.tc.gamegallery.data

import com.tc.gamegallery.GameGenresQuery
import com.tc.gamegallery.domain.GameGenresTags

fun GameGenresQuery.Game.toGames(): GameGenresTags {
    return GameGenresTags(
        name = name ?: "N/A"
    )
}
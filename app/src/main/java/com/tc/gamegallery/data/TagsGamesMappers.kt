package com.tc.gamegallery.data

import com.tc.gamegallery.GameTagsQuery
import com.tc.gamegallery.domain.GameGenresTags

fun GameTagsQuery.Game.toGames(): GameGenresTags {
    return GameGenresTags(
        name = name ?: "N/A"
    )
}
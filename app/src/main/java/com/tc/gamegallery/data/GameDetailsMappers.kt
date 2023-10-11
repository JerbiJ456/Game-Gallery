package com.tc.gamegallery.data

import com.tc.gamegallery.GameDetailsQuery
import com.tc.gamegallery.domain.GameDetails

fun GameDetailsQuery.GameDetails.toGameDetails(): GameDetails {
    return GameDetails(
        name = name ?: "no tag",
        description = description ?: "no description",
        rating = rating?.toFloat() ?: 0.0f
    )
}
package com.tc.gamegallery.data

import com.tc.gamegallery.GameSeriesQuery
import com.tc.gamegallery.domain.Genre

fun GameSeriesQuery.Genre.toGenre(): Genre {
    return Genre(
        name = name ?: "N/A"
    )
}
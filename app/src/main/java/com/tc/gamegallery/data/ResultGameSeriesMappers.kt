package com.tc.gamegallery.data

import com.tc.gamegallery.GameSeriesQuery
import com.tc.gamegallery.domain.ResultGameSeries

fun GameSeriesQuery.Result.toResult(): ResultGameSeries {
    return ResultGameSeries(
        id = id,
        name = name ?: "No Name",
        //backgroundImage = backgroundImage ?: "No background image",
        thumbnailImage = thumbnailImage ?: "No thumbnailImage",
        genres = genres?.mapNotNull { it.toGenre() } ?: emptyList(),
        platforms = parentPlatforms?.mapNotNull { it.toPlatforms() } ?: emptyList()
    )
}
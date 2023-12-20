package com.tc.gamegallery.data

import com.tc.gamegallery.GameSeriesQuery
import com.tc.gamegallery.domain.GameSeries

fun GameSeriesQuery.GameSeries.toGameSeries(): GameSeries {
    return GameSeries(
        nextPage = nextPage,
        results = results.mapNotNull {it.toResult()} ?: emptyList(),
    )
}
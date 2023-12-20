package com.tc.gamegallery.presentation.gamedetail

import com.tc.gamegallery.domain.GameDetails
import com.tc.gamegallery.domain.GameSeries
import com.tc.gamegallery.domain.ResultGameSeries

data class GameDetailScreenState(
    val gameDetails: GameDetails? = GameDetails(),
    val gameSeries: GameSeries = GameSeries(),
    val isLoading: Boolean = false,
    val nextPage: Int? = 1,
    val newPageIsLoading: Boolean = false,
    val currentPage: Int = 0,
    val cachedGameSeries: List<ResultGameSeries> = emptyList()
)

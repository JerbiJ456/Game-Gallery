package com.tc.gamegallery.presentation.gamedetail

import com.tc.gamegallery.domain.GameDetails

data class GameDetailScreenState(
    val gameDetails: GameDetails? = GameDetails(),
    val isLoading: Boolean = false
)

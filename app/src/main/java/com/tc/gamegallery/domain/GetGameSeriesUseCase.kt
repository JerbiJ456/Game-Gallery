package com.tc.gamegallery.domain

import com.apollographql.apollo3.api.Optional

class GetGameSeriesUseCase (
    private val gameClient: GameClient
) {
    suspend fun execute(
        id: Int,
        pageSize: Int,
        page: Int,
    ): GameSeries {
        return gameClient
            .getGameSeries(id, page, pageSize)
    }
}
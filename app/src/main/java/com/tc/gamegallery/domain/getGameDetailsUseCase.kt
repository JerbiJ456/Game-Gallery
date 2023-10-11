package com.tc.gamegallery.domain

import com.apollographql.apollo3.api.Optional

class getGameDetailsUseCase (
    private val gameClient: GameClient
    ) {

    suspend fun execute(id: Int): GameDetails? {
        return gameClient
            .getGameDetails(id)
    }
}
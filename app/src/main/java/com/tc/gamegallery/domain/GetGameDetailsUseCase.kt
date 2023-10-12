package com.tc.gamegallery.domain

class GetGameDetailsUseCase (
    private val gameClient: GameClient
    ) {

    suspend fun execute(id: Int): GameDetails? {
        return gameClient
            .getGameDetails(id)
    }
}
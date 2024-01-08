package com.tc.gamegallery.domain

import com.apollographql.apollo3.api.Optional

class GetFavoriteGamesUseCase (
    private val gameClient: GameClient
) {
    suspend fun execute(
        favoriteGamesIds: List<Int>,
    ): GameCatalog {
        return gameClient
            .getFavoriteGamesCatalog(favoriteGamesIds)
    }
}
package com.tc.gamegallery.domain

import com.apollographql.apollo3.api.Optional

//one use case per request
class GetGameCatalogUseCase(
    private val gameClient: GameClient
) {

    suspend fun execute(pageSize: Optional<Int>, page: Optional<Int>): List<GameCatalog> {
        return gameClient
            .getGamesCatalog(pageSize, page)
            .sortedBy { it.name }
    }
}
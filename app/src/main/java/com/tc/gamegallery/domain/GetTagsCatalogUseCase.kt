package com.tc.gamegallery.domain

import com.apollographql.apollo3.api.Optional

//one use case per request
class GetTagsCatalogUseCase(
    private val gameClient: GameClient
) {

    suspend fun execute(
        pageSize: Optional<Int>,
        page: Optional<Int>,
    ): GenresTags {
        return gameClient
            .getGameTags(pageSize, page)
    }
}
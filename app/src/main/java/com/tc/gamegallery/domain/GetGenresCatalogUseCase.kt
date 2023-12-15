package com.tc.gamegallery.domain

import com.apollographql.apollo3.api.Optional

class GetGenresCatalogUseCase (
    private val gameClient: GameClient
) {
    suspend fun execute(
        pageSize: Optional<Int?>,
        page: Optional<Int?>,
    ): GenresTags {
        return gameClient
            .getGameGenres(pageSize, page)
    }
}
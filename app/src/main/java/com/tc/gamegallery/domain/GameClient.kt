package com.tc.gamegallery.domain

import com.apollographql.apollo3.api.Optional

interface GameClient {

    suspend fun getGamesCatalog(
        pageSize: Optional<Int?>,
        page: Optional<Int?>,
        search: Optional<String?>,
        genres: Optional<String?>
    ) : GameCatalog

    suspend fun getGameDetails(id: Int) : GameDetails?

    suspend fun getGameGenres(
        pageSize: Optional<Int?>,
        page: Optional<Int?>) : GenresTags
}
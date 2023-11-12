package com.tc.gamegallery.domain

import com.apollographql.apollo3.api.Optional

interface GameClient {

    suspend fun getGamesCatalog(
        pageSize: Optional<Int?>,
        page: Optional<Int?>,
        search: Optional<String?>
    ) : List<GameCatalog>

    suspend fun getGameDetails(id: Int) : GameDetails?

    //put other request here for example getGameDetails
}
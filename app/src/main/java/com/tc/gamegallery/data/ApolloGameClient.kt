package com.tc.gamegallery.data

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.tc.gamegallery.GameCatalogQuery
import com.tc.gamegallery.GameDetailsQuery
import com.tc.gamegallery.domain.GameCatalog
import com.tc.gamegallery.domain.GameClient
import com.tc.gamegallery.domain.GameDetails

class ApolloGameClient(
    private val apolloClient: ApolloClient
    ) : GameClient {
    override suspend fun getGamesCatalog(
        pageSize: Optional<Int?>,
        page: Optional<Int?>,
        search: Optional<String?>
    ): List<GameCatalog> {
        return apolloClient
            .query(GameCatalogQuery(pageSize, page, search))
            .execute()
            .data
            ?.allGames
            ?.results
            ?.map { it.toGameCatalog() }
            ?: emptyList()
    }

    override suspend fun getGameDetails(id: Int): GameDetails? {
        return apolloClient
            .query(GameDetailsQuery(id))
            .execute()
            .data
            ?.gameDetails
            ?.toGameDetails()
    }
}
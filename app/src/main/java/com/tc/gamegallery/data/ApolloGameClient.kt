package com.tc.gamegallery.data

import com.apollographql.apollo3.ApolloClient
import com.tc.gamegallery.GameCatalogQuery
import com.tc.gamegallery.domain.GameCatalog
import com.tc.gamegallery.domain.GameClient

class ApolloGameClient(
    private val apolloClient: ApolloClient
    ) : GameClient {
    override suspend fun getGamesCatalog(pageSize: Int): List<GameCatalog> {
        return apolloClient
            .query(GameCatalogQuery(pageSize))
            .execute()
            .data
            ?.allGames
            ?.results
            ?.map { it.toGameCatalog() }
            ?: emptyList()
    }
}
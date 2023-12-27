package com.tc.gamegallery.data

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.tc.gamegallery.GameCatalogQuery
import com.tc.gamegallery.GameDetailsQuery
import com.tc.gamegallery.GameFromIdQuery
import com.tc.gamegallery.GameGenresQuery
import com.tc.gamegallery.GameSeriesQuery
import com.tc.gamegallery.GameTagsQuery
import com.tc.gamegallery.domain.GameCatalog
import com.tc.gamegallery.domain.GameClient
import com.tc.gamegallery.domain.GameDetails
import com.tc.gamegallery.domain.GameSeries
import com.tc.gamegallery.domain.GenresTags
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class ApolloGameClient(
    private val apolloClient: ApolloClient
) : GameClient {
    override suspend fun getGamesCatalog(
        pageSize: Optional<Int?>,
        page: Optional<Int?>,
        search: Optional<String?>,
        genres: Optional<String?>,
        tags: Optional<String?>,
        dates: Optional<String?>
    ): GameCatalog {
        return apolloClient
            .query(GameCatalogQuery(pageSize, page, search, genres, tags, dates))
            .execute()
            .data
            ?.allGames
            ?.toGameCatalog()
            ?: GameCatalog()
    }

    override suspend fun getGameDetails(id: Int): GameDetails {
        return apolloClient
            .query(GameDetailsQuery(id))
            .execute()
            .data
            ?.gameDetails
            ?.toGameDetails()
            ?: GameDetails()
    }

    override suspend fun getGameGenres(
        pageSize: Optional<Int?>,
        page: Optional<Int?>
    ): GenresTags {
        return apolloClient
            .query(GameGenresQuery(pageSize, page))
            .execute()
            .data
            ?.allGenres
            ?.toGenres()
            ?: GenresTags()
    }

    override suspend fun getGameTags(
        pageSize: Optional<Int?>,
        page: Optional<Int?>
    ): GenresTags {
        return apolloClient
            .query(GameTagsQuery(pageSize, page))
            .execute()
            .data
            ?.allTags
            ?.toTags()
            ?: GenresTags()
    }

    override suspend fun getGameSeries(
        id: Int,
        pageSize: Int,
        page: Int,
    ): GameSeries {
        return apolloClient
            .query(GameSeriesQuery(id, pageSize, page))
            .execute()
            .data
            ?.gameSeries
            ?.toGameSeries()
            ?: GameSeries()
    }

    override suspend fun getFavoriteGamesCatalog(favoriteGamesIds: List<Int>): GameCatalog {
        val gamesList = coroutineScope {
            favoriteGamesIds.map { gameId ->
                async {
                    val response = apolloClient.query(GameFromIdQuery(gameId)).execute()
                    response.data?.gameDetails?.toResult()
                }
            }.awaitAll()
        }
        val validGamesList = gamesList.filterNotNull()
        return GameCatalog(results = validGamesList)
    }
}
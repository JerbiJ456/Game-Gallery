package com.tc.gamegallery.data

import com.tc.gamegallery.GameCatalogQuery
import com.tc.gamegallery.domain.GameCatalog

fun GameCatalogQuery.AllGames.toGameCatalog(): GameCatalog {
    return GameCatalog(
        nextPage = nextPage,
        results = results?.mapNotNull {it.toResult()} ?: emptyList(),
    )
}
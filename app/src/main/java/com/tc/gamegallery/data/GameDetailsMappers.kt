package com.tc.gamegallery.data

import com.tc.gamegallery.GameDetailsQuery
import com.tc.gamegallery.domain.EsrbRating
import com.tc.gamegallery.domain.GameDetails
import com.tc.gamegallery.domain.GamePublisher
import com.tc.gamegallery.domain.Genre

fun GameDetailsQuery.GameDetails.toGameDetails(): GameDetails {
    return GameDetails(
        name = name ?: "Name not found",
        description = description!!.ifBlank { "No description" },
        metacritic = metacritic ?: 0,
        genres = genres?.mapNotNull { it.toGenre() } ?: listOf(Genre()),
        esrbRating = EsrbRating(esrbRating?.name ?: "N/A"),
        publishers = publishers?.mapNotNull { it.toPublishers() } ?: listOf(GamePublisher()),
        thumbnailImage = thumbnailImage ?: "",
        screenshots = screenshots?.mapNotNull { it.toScreenshots() } ?: emptyList(),
        backgroundImage = backgroundImageAdditional ?: "",
        stores = stores?.mapNotNull { it.toStores() } ?: emptyList()
    )
}
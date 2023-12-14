package com.tc.gamegallery.data

import com.tc.gamegallery.GameDetailsQuery
import com.tc.gamegallery.domain.EsrbRating
import com.tc.gamegallery.domain.GameDetails

fun GameDetailsQuery.GameDetails.toGameDetails(): GameDetails {
    return GameDetails(
        name = name ?: "Name not found",
        description = description ?: "No description",
        metacritic = metacritic ?: 0,
        genres = genres?.mapNotNull { it.toGenre() } ?: emptyList(),
        esrbRating = EsrbRating(esrbRating?.name ?: "N/A"),
        publishers = publishers?.mapNotNull { it.toPublishers() } ?: emptyList(),
        thumbnailImage = thumbnailImage ?: "",
        screenshots = screenshots?.mapNotNull { it.toScreenshots() } ?: emptyList(),
        backgroundImage = backgroundImageAdditional ?: ""
    )
}
package com.tc.gamegallery.data

import com.tc.gamegallery.GameDetailsQuery
import com.tc.gamegallery.domain.GamePublisher

fun GameDetailsQuery.Publisher.toPublishers(): GamePublisher {
    return GamePublisher(
        name = name ?: "N/A"
    )
}
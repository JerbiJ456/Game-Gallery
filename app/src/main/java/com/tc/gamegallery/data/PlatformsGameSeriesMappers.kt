package com.tc.gamegallery.data

import com.tc.gamegallery.GameSeriesQuery
import com.tc.gamegallery.domain.Platform

fun GameSeriesQuery.ParentPlatform.toPlatforms(): Platform {
    return Platform(
        slug = platform.slug ?: ""
    )
}
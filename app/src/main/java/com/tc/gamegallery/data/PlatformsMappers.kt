package com.tc.gamegallery.data

import com.tc.gamegallery.GameCatalogQuery
import com.tc.gamegallery.domain.Platform

fun GameCatalogQuery.ParentPlatform.toPlatforms(): Platform {
    return Platform(
        slug = platform.slug ?: ""
    )
}
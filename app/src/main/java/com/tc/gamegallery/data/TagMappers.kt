package com.tc.gamegallery.data

import com.tc.gamegallery.GameCatalogQuery
import com.tc.gamegallery.GameFromIdQuery
import com.tc.gamegallery.domain.Tag

fun GameCatalogQuery.Tag.toTag(): Tag {
    return Tag(
        name = name ?: "no tag"
    )
}

fun GameFromIdQuery.Tag.toTag(): Tag {
    return Tag(
        name = name ?: "no tag"
    )
}
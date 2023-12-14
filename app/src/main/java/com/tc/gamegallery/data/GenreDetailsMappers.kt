package com.tc.gamegallery.data

import com.tc.gamegallery.GameDetailsQuery
import com.tc.gamegallery.domain.Genre

fun GameDetailsQuery.Genre.toGenre(): Genre {
    return Genre(
        name = name ?: "no genre"
    )
}
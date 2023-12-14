package com.tc.gamegallery.data

import com.tc.gamegallery.GameDetailsQuery
import com.tc.gamegallery.domain.EsrbRating

fun GameDetailsQuery.EsrbRating.toEsrbRating(): EsrbRating {
    return EsrbRating(
        name = name ?: "no genre"
    )
}
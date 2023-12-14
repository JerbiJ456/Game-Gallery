package com.tc.gamegallery.data

import com.tc.gamegallery.GameDetailsQuery
import com.tc.gamegallery.domain.Screenshot

fun GameDetailsQuery.Screenshot.toScreenshots(): Screenshot {
    return Screenshot(
        image = image ?: ""
    )
}
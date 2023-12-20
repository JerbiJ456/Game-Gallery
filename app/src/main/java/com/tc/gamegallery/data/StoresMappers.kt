package com.tc.gamegallery.data

import com.tc.gamegallery.GameDetailsQuery
import com.tc.gamegallery.domain.Store

fun GameDetailsQuery.Store.toStores(): Store {
    return Store(
        name = store.name ?: "",
        domain = store.domain ?: ""
    )
}
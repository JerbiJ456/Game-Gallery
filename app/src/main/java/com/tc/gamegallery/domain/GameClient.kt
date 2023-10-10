package com.tc.gamegallery.domain

interface GameClient {

    suspend fun getGamesCatalog(pageSize: Int) : List<GameCatalog>
}
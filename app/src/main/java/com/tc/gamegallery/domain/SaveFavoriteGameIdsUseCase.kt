package com.tc.gamegallery.domain

import android.content.SharedPreferences

class SaveFavoriteGameIdsUseCase (
    private val sharedPreferences: SharedPreferences
) {
    suspend fun execute(
        favoriteGameIds: List<Int>
    ) {
        val editor = sharedPreferences.edit()

        // Conversion de la liste en une chaîne de caractères
        val listAsString = favoriteGameIds.joinToString(",")

        // Stockage de la liste dans SharedPreferences
        editor.putString("liked_game_ids", listAsString)
        editor.apply()
    }
}
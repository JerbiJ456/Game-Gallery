package com.tc.gamegallery.domain

import android.content.SharedPreferences

class GetFavoriteGameIdsUseCase (
    private val sharedPreferences: SharedPreferences
) {
    fun execute(): List<Int> {
        try {
            val storedListAsString = sharedPreferences.getString("liked_game_ids", "")
            val likedGameIds = storedListAsString?.split(",")?.map { it.toInt() } ?: emptyList()
            return likedGameIds
        } catch (exception: NumberFormatException) {
            val editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()
            return emptyList<Int>()
        }
    }
}
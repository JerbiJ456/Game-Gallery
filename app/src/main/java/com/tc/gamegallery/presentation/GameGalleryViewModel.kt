package com.tc.gamegallery.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameGalleryViewModel @Inject constructor(): ViewModel() {
    private val currentActivity = mutableStateOf("Games")
    private val showArrow = mutableStateOf(false)

    fun shouldShowArrow(): Boolean {
        return showArrow.value
    }

    fun getCurrentActivity(): String {
        return currentActivity.value
    }

    fun updateActivity(newCurrentActivity: String) {
        currentActivity.value = newCurrentActivity
    }

    fun updateArrow(show: Boolean) {
        showArrow.value = show
    }

}
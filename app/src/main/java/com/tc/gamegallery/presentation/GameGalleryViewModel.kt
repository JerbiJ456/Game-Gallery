package com.tc.gamegallery.presentation

import android.util.Log
import androidx.annotation.Px
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameGalleryViewModel @Inject constructor(): ViewModel() {
    private val currentActivity = mutableStateOf("Games")
    private val showArrow = mutableStateOf(false)
    private val searchOpen = mutableStateOf(false)
    private var lastScrollIndex = 0
    private val _scrollUp = MutableLiveData(false)
    private val selectedTabRow = mutableIntStateOf(0)
    val scrollUp: LiveData<Boolean>
        get() = _scrollUp

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

    fun updateScrollPosition(newScrollIndex: Int) {
        if (newScrollIndex == lastScrollIndex) return
        _scrollUp.value = newScrollIndex > lastScrollIndex
        lastScrollIndex = newScrollIndex
    }

    fun updateSearchOpen() {
        searchOpen.value = !searchOpen.value
    }

    fun isSearchOpen(): Boolean {
        return searchOpen.value
    }

    fun changeSelectedTabRow(index: Int) {
        selectedTabRow.value = index
    }

    fun getSelectedTabRow(): Int {
        return selectedTabRow.value
    }

}
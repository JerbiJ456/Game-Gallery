package com.tc.gamegallery.presentation.gamedetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.api.BooleanExpression
import com.tc.gamegallery.domain.GetGameDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameDetailScreenViewModel @Inject constructor (
    private val getGameDetailsUseCase: GetGameDetailsUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(GameDetailScreenState())
    val state: StateFlow<GameDetailScreenState> = _state.asStateFlow()

    private var selectedGameId by mutableIntStateOf(0)

    init {
        reset()
    }

    fun reset() {
        selectedGameId = 1
        _state.value = GameDetailScreenState()
    }

    fun updateGameId(id: Int) {
        selectedGameId = id
        updateGameState()
    }

    private fun updateGameState() {
        viewModelScope.launch {
            _state.update {it.copy(
                isLoading = true
            )
            }
            _state.update { it.copy(
                    gameDetails = getGameDetailsUseCase.execute(selectedGameId),
                    isLoading = false
                )
            }
        }
    }

}
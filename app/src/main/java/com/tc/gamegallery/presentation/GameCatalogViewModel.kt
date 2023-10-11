package com.tc.gamegallery.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.api.Optional
import com.tc.gamegallery.domain.GameCatalog
import com.tc.gamegallery.domain.GameDetails
import com.tc.gamegallery.domain.getGameCatalogUseCase
import com.tc.gamegallery.domain.getGameDetailsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GameCatalogViewModel(
    private val getGameCatalogUseCase: getGameCatalogUseCase,
    private val getGameDetailsUseCase: getGameDetailsUseCase
): ViewModel() {

    private val _state = MutableStateFlow(GamesCatalogState())
    val state = _state.asStateFlow() //only the viewModel can change the state, the UI only have a immutable version of the state

    init {
        viewModelScope.launch {
            _state.update {it.copy(
                isLoading = true
            ) }

            _state.update { it.copy(
                gamesCatalog = getGameCatalogUseCase.execute(Optional.present(10)),
                isLoading = false
            ) }
        }
    }

    fun selectGame(id: Int) {
        viewModelScope.launch {
            _state.update { it.copy(
                selectedGame = getGameDetailsUseCase.execute(id)
            ) }
        }
    }

    fun dismissGameDetails() {
        _state.update { it.copy(
            selectedGame = null
        ) }
    }

    data class GamesCatalogState(
        val gamesCatalog: List<GameCatalog> = emptyList(),
        val isLoading: Boolean = false,
        val selectedGame: GameDetails? = null
    )

}
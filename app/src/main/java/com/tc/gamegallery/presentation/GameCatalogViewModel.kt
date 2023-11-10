package com.tc.gamegallery.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.api.Optional
import com.tc.gamegallery.domain.GameCatalog
import com.tc.gamegallery.domain.GameDetails
import com.tc.gamegallery.domain.GetGameCatalogUseCase
import com.tc.gamegallery.domain.GetGameDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameCatalogViewModel @Inject constructor(
    private val getGameCatalogUseCase: GetGameCatalogUseCase,
    private val getGameDetailsUseCase: GetGameDetailsUseCase
): ViewModel() {

    private val _state = MutableStateFlow(GamesCatalogState())
    val state = _state.asStateFlow() //only the viewModel can change the state, the UI only have a immutable version of the state
    private val searchDebounce = 1000L
    private var searchJob: Job? = null
    init {
        viewModelScope.launch {
            _state.update {it.copy(
                isLoading = true
            ) }

            _state.update { it.copy(
                gamesCatalog = getGameCatalogUseCase.execute(
                    Optional.present(10),
                    Optional.present(1),
                    Optional.present("")
                ),
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

    fun nextPage() {
        val pageNumber = (_state.value.currentPage + 1).coerceIn(1, 15)
        _state.update { it.copy(
            currentPage = pageNumber
        ) }
        updatePage()
    }

    fun previousPage() {
        val pageNumber = (_state.value.currentPage - 1).coerceIn(1, 15)
        _state.update { it.copy(
            currentPage = pageNumber
        ) }
        updatePage()
    }

    fun search(search: String) {
        searchJob?.cancel()

        searchJob = viewModelScope.launch {
            _state.update { it.copy(
                currentSearch = search
            ) }
            delay(searchDebounce)
            updatePage()
        }
    }

    private fun updatePage() {
        viewModelScope.launch {
            _state.update {it.copy(
                isLoading = true
            ) }

            _state.update { it.copy(
                gamesCatalog = getGameCatalogUseCase.execute(
                    Optional.present(10),
                    Optional.present(_state.value.currentPage),
                    Optional.present(_state.value.currentSearch)
                ),
                isLoading = false,
            ) }
        }
    }

    data class GamesCatalogState(
        val gamesCatalog: List<GameCatalog> = emptyList(),
        val isLoading: Boolean = false,
        val selectedGame: GameDetails? = null,
        val currentPage: Int = 1,
        val currentSearch: String = ""
    )

}
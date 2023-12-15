package com.tc.gamegallery.presentation.gamecatalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.api.Optional
import com.tc.gamegallery.domain.GameCatalog
import com.tc.gamegallery.domain.GameDetails
import com.tc.gamegallery.domain.GetGameCatalogUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.tc.gamegallery.domain.ResultGames

@HiltViewModel
class GameCatalogViewModel @Inject constructor(
    private val getGameCatalogUseCase: GetGameCatalogUseCase,
): ViewModel() {

    private val _state = MutableStateFlow(GamesCatalogState())
    val state = _state.asStateFlow() //only the viewModel can change the state, the UI only have a immutable version of the state
    private val searchDebounce = 1000L
    private var searchJob: Job? = null
    private var cachedGames = listOf<ResultGames>()
    private var genres: String? = ""
    private var tags: String? = ""

    fun getCallInfo(genre: String?, tag: String?) {
        genres = genre
        tags = tag
        cachedGames = emptyList()
        _state.update {it.copy(
            nextPage = 1,
            results = cachedGames
        ) }
        updatePage()
    }

    fun nextPage() {
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
            if (_state.value.nextPage != null) {
                _state.update {
                    it.copy(
                        newPageIsLoading = true
                    )
                }

                _state.update {
                    it.copy(
                        gamesCatalog = getGameCatalogUseCase.execute(
                            Optional.present(10),
                            Optional.present(_state.value.nextPage!!),
                            Optional.present(_state.value.currentSearch),
                            Optional.present(genres)
                        ),
                        newPageIsLoading = true,
                        isLoading = false,
                    )
                }

                cachedGames += _state.value.gamesCatalog.results

                _state.update {
                    it.copy(
                        currentPage = _state.value.nextPage!!,
                        nextPage = _state.value.gamesCatalog.nextPage,
                        results = cachedGames
                    )
                }
            }
        }
    }

    data class GamesCatalogState(
        val results: List<ResultGames> = emptyList(),
        val gamesCatalog: GameCatalog = GameCatalog(),
        val isLoading: Boolean = true,
        val selectedGame: GameDetails? = null,
        val currentPage: Int = 1,
        val currentSearch: String = "",
        val nextPage: Int? = 1,
        val newPageIsLoading: Boolean = false,
    )

}
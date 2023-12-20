package com.tc.gamegallery.presentation.gamedetail

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.exception.ApolloException
import com.tc.gamegallery.domain.GetGameDetailsUseCase
import com.tc.gamegallery.domain.GetGameSeriesUseCase
import com.tc.gamegallery.domain.ResultGameSeries
import com.tc.gamegallery.domain.ResultGenresTags
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameDetailScreenViewModel @Inject constructor (
    private val getGameDetailsUseCase: GetGameDetailsUseCase,
    private val getGameSeriesUseCase: GetGameSeriesUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(GameDetailScreenState())
    val state: StateFlow<GameDetailScreenState> = _state.asStateFlow()

    private var selectedGameId by mutableIntStateOf(0)
    private var cachedGames = listOf<ResultGameSeries>()

    init {
        reset()
    }

    fun reset() {
        selectedGameId = 1
        _state.value = GameDetailScreenState()
    }

    fun updateGameId(id: Int) {
        selectedGameId = id
        _state.value = GameDetailScreenState()
        updateGameState()
        cachedGames = listOf()
        nextPage()
    }

    fun nextPage() {
        if (_state.value.nextPage != null) {
            viewModelScope.launch {
                _state.update {it.copy(
                    newPageIsLoading = true
                )
                }
                try {
                    _state.update { it.copy(
                        gameSeries = getGameSeriesUseCase.execute(id = selectedGameId, pageSize = 4, page = _state.value.currentPage + 1),
                        newPageIsLoading = false
                    )
                    }
                    cachedGames += _state.value.gameSeries.results
                    _state.update {
                        it.copy(
                            nextPage = _state.value.gameSeries.nextPage,
                            cachedGameSeries = cachedGames,
                            currentPage = _state.value.currentPage + 1
                        )
                    }
                } catch (exception: ApolloException) {
                    Log.d("apollo", "failed")
                }
            }
        }
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
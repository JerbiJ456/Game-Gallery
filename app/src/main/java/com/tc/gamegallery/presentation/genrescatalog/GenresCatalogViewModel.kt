package com.tc.gamegallery.presentation.genrescatalog

import android.text.BoringLayout
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.api.Optional
import com.tc.gamegallery.domain.GameCatalog
import com.tc.gamegallery.domain.GameDetails
import com.tc.gamegallery.domain.GenresTags
import com.tc.gamegallery.domain.GetGenresCatalogUseCase
import com.tc.gamegallery.domain.ResultGenresTags
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenresCatalogViewModel @Inject constructor(
    private val getGameGenres: GetGenresCatalogUseCase,
): ViewModel() {

    private val _state = MutableStateFlow(GamesCatalogState())
    val state = _state.asStateFlow() //only the viewModel can change the state, the UI only have a immutable version of the state
    private var cachedGenres = listOf<ResultGenresTags>()
    init {
        viewModelScope.launch {
            _state.update {it.copy(
                isLoading = true
            ) }

            _state.update { it.copy(
                genresCatalog = getGameGenres.execute(
                    Optional.present(10),
                    Optional.present(1)
                ),
                isLoading = false
            ) }

            cachedGenres += _state.value.genresCatalog.results

            _state.update { it.copy(
                nextPage = _state.value.genresCatalog.nextPage,
                results = cachedGenres
            ) }
        }
    }

    fun nextPage() {
        updatePage()
    }

    private fun updatePage() {
        viewModelScope.launch {
            if (_state.value.nextPage != null) {
                _state.update {
                    it.copy(
                        isLoading = false
                    )
                }

                _state.update {
                    it.copy(
                        genresCatalog = getGameGenres.execute(
                            Optional.present(10),
                            Optional.present(_state.value.nextPage!!)
                        ),
                        isLoading = false,
                        currentPage = _state.value.currentPage + 1
                    )
                }

                cachedGenres += _state.value.genresCatalog.results

                _state.update {
                    it.copy(
                        nextPage = _state.value.genresCatalog.nextPage,
                        results = cachedGenres
                    )
                }
            }
        }
    }

    data class GamesCatalogState(
        val results: List<ResultGenresTags> = emptyList(),
        val genresCatalog: GenresTags = GenresTags(),
        val isLoading: Boolean = false,
        val selectedGame: GameDetails? = null,
        val currentPage: Int = 1,
        val currentSearch: String = "",
        val nextPage: Int? = null,
        val isEndReached: Boolean = false
    )

}
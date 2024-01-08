package com.tc.gamegallery.presentation.gamecatalog

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.exception.ApolloException
import com.tc.gamegallery.domain.GameCatalog
import com.tc.gamegallery.domain.GameDetails
import com.tc.gamegallery.domain.GetFavoriteGameIdsUseCase
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
import com.tc.gamegallery.domain.SaveFavoriteGameIdsUseCase
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Period
import java.time.format.DateTimeFormatter

@HiltViewModel
class GameCatalogViewModel @Inject constructor(
    private val getGameCatalogUseCase: GetGameCatalogUseCase,
    private val saveFavoriteGameIdsUseCase: SaveFavoriteGameIdsUseCase,
    private val getFavoriteGameIdsUseCase: GetFavoriteGameIdsUseCase
): ViewModel() {
    private val _state = MutableStateFlow(GamesCatalogState())
    val state = _state.asStateFlow() //only the viewModel can change the state, the UI only have a immutable version of the state
    private val searchDebounce = 1000L
    private var searchJob: Job? = null
    private var cachedGames = listOf<ResultGames>()
    private var cachedNewGames = listOf<ResultGames>()
    private var cachedUpcomingGames = listOf<ResultGames>()
    private var genres: String? = ""
    private var tags: String? = ""
    private val currentDate = LocalDate.now()
    private val newReleasesDate = listOf(currentDate.minusMonths(2), currentDate).joinToString(",")
    private val upcomingReleases = listOf(currentDate, currentDate.plusMonths(4)).joinToString(",")
    var favoriteGameIds = listOf<Int>()

    fun getCallInfo(genre: String?, tag: String?) {
        genres = genre
        tags = tag
        cachedGames = emptyList()
        favoriteGameIds = getFavoriteGameIdsUseCase.execute()
        _state.update {it.copy(
            isLoading = true,
            nextPage = 1,
            results = cachedGames
        ) }
        updatePage()
    }

    fun nextPage() {
        updatePage()
    }

    fun onSearchTextChange(search: String) {
        searchJob?.cancel()

        searchJob = viewModelScope.launch {
            _state.update { it.copy(
                currentSearch = search,
                nextPage = 1
            ) }
            delay(searchDebounce)
            _state.update { it.copy(
                isLoading = true,
            ) }
            cachedGames = emptyList()
            updatePage()
        }
    }

    fun nextPageNewReleases() {
        updateNewReleases()
    }

    fun addFavoriteGameId(gameId: Int) {
        favoriteGameIds = favoriteGameIds + gameId
        viewModelScope.launch {
            saveFavoriteGameIdsUseCase.execute(favoriteGameIds)
        }
    }

    fun removeFavoriteGameId(gameId: Int) {
        favoriteGameIds = favoriteGameIds.filter { it != gameId }
        viewModelScope.launch {
            saveFavoriteGameIdsUseCase.execute(favoriteGameIds)
        }
    }

    fun isGameIdInFavorite(gameId: Int): Boolean {
        return gameId in favoriteGameIds
    }

    private fun updateNewReleases() {
        viewModelScope.launch {
            if (_state.value.newReleasesNextPage != null) {

                _state.update {
                    it.copy(
                        newPageIsLoading = _state.value.newReleasesNextPage != 1,
                    )
                }

                try {
                    _state.update {
                        it.copy(
                            gamesCatalog = getGameCatalogUseCase.execute(
                                Optional.present(10),
                                Optional.present(_state.value.newReleasesNextPage!!),
                                Optional.present(""),
                                Optional.present(genres),
                                Optional.present(tags),
                                Optional.present(newReleasesDate)
                            ),
                            isLoading = false,
                        )
                    }

                    cachedNewGames += _state.value.gamesCatalog.results

                    _state.update {
                        it.copy(
                            newPageIsLoading = false,
                            newReleasesCurrentPage = _state.value.newReleasesNextPage!!,
                            newReleasesNextPage = _state.value.gamesCatalog.nextPage,
                            newReleases = cachedNewGames
                        )
                    }
                } catch (exception: ApolloException) {
                    Log.d("apollo", "failed")
                }
            }
        }
    }

    fun nextPageUpcomingReleases() {
        updateUpcomingReleases()
    }

    private fun updateUpcomingReleases() {
        viewModelScope.launch {
            if (_state.value.upcomingReleasesNextPage != null) {

                _state.update {
                    it.copy(
                        newPageIsLoading = _state.value.upcomingReleasesNextPage != 1,
                    )
                }

                try {
                    _state.update {
                        it.copy(
                            gamesCatalog = getGameCatalogUseCase.execute(
                                Optional.present(10),
                                Optional.present(_state.value.upcomingReleasesNextPage!!),
                                Optional.present(""),
                                Optional.present(genres),
                                Optional.present(tags),
                                Optional.present(upcomingReleases)
                            ),
                            isLoading = false,
                        )
                    }

                    cachedUpcomingGames += _state.value.gamesCatalog.results

                    _state.update {
                        it.copy(
                            newPageIsLoading = false,
                            upcomingReleasesCurrentPage = _state.value.upcomingReleasesNextPage!!,
                            upcomingReleasesNextPage = _state.value.gamesCatalog.nextPage,
                            upcomingReleases = cachedUpcomingGames
                        )
                    }
                } catch (exception: ApolloException) {
                    Log.d("apollo", "failed")
                }
            }
        }
    }

    private fun updatePage() {
        viewModelScope.launch {
            if (_state.value.nextPage != null) {

                _state.update {
                    it.copy(
                        newPageIsLoading = _state.value.nextPage != 1,
                    )
                }
                try {
                    _state.update {
                        it.copy(
                            gamesCatalog = getGameCatalogUseCase.execute(
                                Optional.present(10),
                                Optional.present(_state.value.nextPage!!),
                                Optional.present(_state.value.currentSearch),
                                Optional.present(genres),
                                Optional.present(tags),
                                Optional.present("")
                            ),
                            isLoading = false,
                        )
                    }

                    cachedGames += _state.value.gamesCatalog.results

                    _state.update {
                        it.copy(
                            newPageIsLoading = false,
                            currentPage = _state.value.nextPage!!,
                            nextPage = _state.value.gamesCatalog.nextPage,
                            results = cachedGames
                        )
                    }
                } catch (exception: ApolloException) {
                    Log.d("apollo", "failed")
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
        val newReleasesCurrentPage: Int = 1,
        val upcomingReleasesCurrentPage: Int = 1,
        val currentSearch: String = "",
        val nextPage: Int? = 1,
        val newReleasesNextPage: Int? = 1,
        val upcomingReleasesNextPage: Int? = 1,
        val newPageIsLoading: Boolean = false,
        val newReleases: List<ResultGames> = emptyList(),
        val upcomingReleases: List<ResultGames> = emptyList()
    )

}
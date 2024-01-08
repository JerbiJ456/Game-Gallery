package com.tc.gamegallery.presentation.favoritecatalog

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.exception.ApolloException
import com.tc.gamegallery.domain.GameCatalog
import com.tc.gamegallery.domain.GameDetails
import com.tc.gamegallery.domain.GetFavoriteGameIdsUseCase
import com.tc.gamegallery.domain.GetFavoriteGamesUseCase
import com.tc.gamegallery.domain.ResultGames
import com.tc.gamegallery.domain.SaveFavoriteGameIdsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getFavoriteGamesUseCase: GetFavoriteGamesUseCase,
    private val getFavoriteGameIdsUseCase: GetFavoriteGameIdsUseCase,
    private val saveFavoriteGameIdsUseCase: SaveFavoriteGameIdsUseCase
): ViewModel() {
    private val _state = MutableStateFlow(GamesCatalogState())
    val state = _state.asStateFlow()
    private var cachedGames = listOf<ResultGames>()
    private var favoriteGameIds = listOf<Int>()

    init {
        updatePage()
    }

     fun getFavoriteGameIds(): List<Int> {
//         viewModelScope.launch {
//             saveFavoriteGameIdsUseCase.execute(listOf(1,2))
//         }
        favoriteGameIds = getFavoriteGameIdsUseCase.execute()
        return favoriteGameIds
    }

    fun removeFavoriteGameId(gameId: Int) {
        cachedGames = cachedGames.filter { it.id != gameId }
        _state.update {
            it.copy(
                newPageIsLoading = false,
                results = cachedGames
            )
        }

        favoriteGameIds = favoriteGameIds.filter { it != gameId }
        viewModelScope.launch {
            saveFavoriteGameIdsUseCase.execute(favoriteGameIds)
        }
    }

    fun updatePage() {
        viewModelScope.launch {
            try {
                _state.update {
                    it.copy(
                        gamesCatalog = getFavoriteGamesUseCase.execute(
                            getFavoriteGameIds()
                        ),
                        isLoading = false,
                    )
                }

                cachedGames = _state.value.gamesCatalog.results

                _state.update {
                    it.copy(
                        newPageIsLoading = false,
                        results = cachedGames
                    )
                }
            } catch (exception: ApolloException) {
                Log.d("apollo", "failed")
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
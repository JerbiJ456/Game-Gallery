package com.tc.gamegallery.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tc.gamegallery.presentation.genrescatalog.GenresCatalogScreen
import com.tc.gamegallery.presentation.tagscatalog.TagsCatalogScreen
import com.tc.gamegallery.presentation.favoritecatalog.FavoriteScreen
import com.tc.gamegallery.presentation.gamecatalog.GameCatalogViewModel
import com.tc.gamegallery.presentation.gamecatalog.gameCatalogScreen
import com.tc.gamegallery.presentation.gamedetail.GameDetailScreen
import com.tc.gamegallery.presentation.gamedetail.GameDetailScreenViewModel

@Composable
fun GameGalleryScreen() {
    val viewModelCatalog = hiltViewModel<GameCatalogViewModel>()
    val stateCatalog by viewModelCatalog.state.collectAsState()
    val detailViewModel = hiltViewModel<GameDetailScreenViewModel>()
    val detailState by detailViewModel.state.collectAsState()
    val currentActivity = remember {
        mutableStateOf("Games")
    }
    val showArrow = remember {
        mutableStateOf(false)
    }
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            TopBar(navController = navController, currentActivity, showArrow = showArrow)
        },
        bottomBar = {
            BottomNavBar(navController = navController, onDismissGameDetails = { viewModelCatalog.dismissGameDetails() })
        }
    ) {innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavHost(navController = navController, startDestination = "games") {
                composable("games") {currentActivity.value = "Games"; gameCatalogScreen(
                    state = stateCatalog,
                    onSelectGame = { viewModelCatalog.selectGame(it) },
                    onDismissGameDetails = {},
                    onNextPage = { viewModelCatalog.nextPage() },
                    onPreviousPage = { viewModelCatalog.previousPage() },
                    onSearch = { viewModelCatalog.search(it) },
                    navController = navController
                )
                }
                composable("genres") { currentActivity.value = "Genres"; showArrow.value = false; GenresCatalogScreen() }
                composable("tags") { currentActivity.value = "Tags"; showArrow.value = false; TagsCatalogScreen() }
                composable("favorite") { currentActivity.value = "Favorite"; showArrow.value = false; FavoriteScreen() }
                composable("detail/{game}/{id}",
                    arguments = listOf(
                        navArgument("game") {
                            type = NavType.StringType
                        },
                        navArgument("id") {
                            type = NavType.IntType
                        }
                    )
                ) {
                    val gameName = remember {
                        val name =  it.arguments?.getString("name")
                        name ?: "Game"
                    }
                    val id = remember {
                        val id = it.arguments?.getInt("id")
                        id ?: 1
                    }
                    currentActivity.value = gameName
                    showArrow.value = true
                    GameDetailScreen(id = id, detailViewModel, detailState)
                }
            }
        }
    }
}
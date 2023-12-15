package com.tc.gamegallery.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
import com.tc.gamegallery.presentation.genrescatalog.GenresCatalogViewModel
import com.tc.gamegallery.presentation.tagscatalog.TagsCatalogViewModel

@Composable
fun GameGalleryScreen() {
    val viewModelCatalog = hiltViewModel<GameCatalogViewModel>()
    val detailViewModel = hiltViewModel<GameDetailScreenViewModel>()
    val viewModelGenres = hiltViewModel<GenresCatalogViewModel>()
    val viewModelTags = hiltViewModel<TagsCatalogViewModel>()
    val appViewModel = hiltViewModel<GameGalleryViewModel>()
    val navController = rememberNavController()
    val scrollUpState = appViewModel.scrollUp.observeAsState()
    Scaffold(
        topBar = {
            TopBar(navController = navController, viewModel = appViewModel, scrollUpState = scrollUpState)
        },
        bottomBar = {
            BottomNavBar(navController = navController, )
        }
    ) {innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavHost(navController = navController, startDestination = "games?genres={genres}&tags={tags}&genresName={genresName}&tagsName={tagsName}") {
                composable("games?genres={genres}&tags={tags}&genresName={genresName}&tagsName={tagsName}",
                    arguments = listOf(
                        navArgument("genres") {
                            type = NavType.StringType
                            defaultValue = ""
                        },
                        navArgument("tags") {
                            type = NavType.StringType
                            defaultValue = ""
                        },
                        navArgument("genresName") {
                            type = NavType.StringType
                            defaultValue = ""
                        },
                        navArgument("tagsName") {
                            type = NavType.StringType
                            defaultValue = ""
                        }
                    )
                ) {
                    val genres = remember {
                        val genre = it.arguments?.getString("genres")
                        if (genre == "") null else genre
                    }
                    val tags = remember {
                        val tag = it.arguments?.getString("tags")
                        if (tag == "") null else tag
                    }
                    val genresName = remember {
                        val genre = it.arguments?.getString("genresName")
                        if (genre == "") null else genre
                    }
                    val tagsName = remember {
                        val tag = it.arguments?.getString("tagsName")
                        if (tag == "") null else tag
                    }
                    var newActivity = genresName ?: tagsName
                    if (newActivity == null) newActivity = "Games" else newActivity += " games"
                    val showArrow = newActivity != "Games"
                    appViewModel.updateActivity(newActivity)
                    appViewModel.updateArrow(showArrow)
                    appViewModel.updateScrollPosition(0)
                    appViewModel.updateScrollPositionDetails(5)
                    gameCatalogScreen(
                        viewModel = viewModelCatalog,
                        navController = navController,
                        appViewModel = appViewModel,
                        genres = genres,
                        tags = tags
                    )
                }
                composable("genres") {
                    appViewModel.updateActivity("Genres");
                    appViewModel.updateArrow(false)
                    appViewModel.updateScrollPosition(0)
                    appViewModel.updateScrollPositionDetails(5)
                    GenresCatalogScreen(
                        viewModel = viewModelGenres,
                        navController = navController,
                        appViewModel = appViewModel
                    )
                }
                composable("tags") {
                    appViewModel.updateActivity("Tags")
                    appViewModel.updateArrow(false)
                    appViewModel.updateScrollPosition(0)
                    appViewModel.updateScrollPositionDetails(5)
                    TagsCatalogScreen(
                        viewModel = viewModelTags,
                        navController = navController,
                        appViewModel = appViewModel
                        )
                }
                composable("favorite") {
                    appViewModel.updateActivity("Favorite")
                    appViewModel.updateArrow(false)
                    FavoriteScreen()
                }
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
                        val name =  it.arguments?.getString("game")
                        name ?: "Game"
                    }
                    val id = remember {
                        val id = it.arguments?.getInt("id")
                        id ?: 1
                    }
                    appViewModel.updateActivity(gameName)
                    appViewModel.updateArrow(true)
                    appViewModel.updateScrollPosition(-1)
                    appViewModel.updateScrollPositionDetails(5)
                    GameDetailScreen(id = id, detailsViewModal = detailViewModel, appViewModel = appViewModel)
                }
            }
        }
    }
}
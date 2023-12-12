package com.tc.gamegallery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tc.gamegallery.presentation.BottomNavBar
import com.tc.gamegallery.presentation.FavoriteScreen
import com.tc.gamegallery.presentation.GameCatalogViewModel
import com.tc.gamegallery.presentation.GameDetailScreen
import com.tc.gamegallery.presentation.GenresCatalogScreen
import com.tc.gamegallery.presentation.TagsCatalogScreen
import com.tc.gamegallery.presentation.gameCatalogScreen
import com.tc.gamegallery.ui.theme.GameGalleryTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GameGalleryTheme {
                val viewModel = hiltViewModel<GameCatalogViewModel>()
                val state by viewModel.state.collectAsState()
                var currentActivity by rememberSaveable {
                    mutableStateOf("Games")
                }
                val navController = rememberNavController()
                Scaffold(
                    topBar = {
                             TopAppBar(title = { Text(text = currentActivity, color = Color.White) }, backgroundColor = Color(0xFF0f172a))
                    },
                    bottomBar = {
                        BottomNavBar(navController)
                    }
                ) {innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        NavHost(navController = navController, startDestination = "games") {
                            composable("games") {currentActivity = "Games"; gameCatalogScreen(
                                state = state,
                                onSelectGame = { viewModel.selectGame(it) },
                                onDismissGameDetails = {},
                                onNextPage = { viewModel.nextPage() },
                                onPreviousPage = { viewModel.previousPage() },
                                onSearch = { viewModel.search(it.toString()) }
                            )}
                            composable("genres") { currentActivity = "Genres"; GenresCatalogScreen() }
                            composable("tags") { currentActivity = "Tags"; TagsCatalogScreen() }
                            composable("favorite") { currentActivity = "Favorite"; FavoriteScreen() }
                            composable("detail/{game}") {navBackStackEntry -> currentActivity = checkNotNull(navBackStackEntry.arguments?.getString("game")) ; GameDetailScreen(navBackStackEntry.arguments?.getString("game")) }
                        }
                    }
                }
            }
        }
    }
}

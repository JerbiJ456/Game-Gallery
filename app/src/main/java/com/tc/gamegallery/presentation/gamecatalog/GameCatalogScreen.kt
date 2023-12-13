package com.tc.gamegallery.presentation.gamecatalog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tc.gamegallery.presentation.GameGalleryScreen

@Composable
fun gameCatalogScreen(
    state: GameCatalogViewModel.GamesCatalogState,
    onSelectGame: (id: Int) -> Unit,
    onDismissGameDetails: () -> Unit,
    onNextPage: () -> Unit,
    onPreviousPage: () -> Unit,
    onSearch: (search: String) -> Unit,
    navController: NavController
) {
    Surface (color = Color(0xFF1e293b)) {
        Box(modifier = Modifier.fillMaxSize()) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.padding(end = 16.dp, top = 70.dp, start = 16.dp), // Pour ajouter un peu d'espace au bas de la liste
                    horizontalArrangement = Arrangement.spacedBy(8.dp) // espace entre les cartes sur l'axe horizontal
                ) {
                    items(state.gamesCatalog) { game ->
                        GameTile(
                            game,
                            navController = navController
                        )
                    }
                }
                TextField(
                    shape = RoundedCornerShape(5.dp),
                    value = state.currentSearch,
                    onValueChange = { onSearch(it) },
                    label = { Text("Search") },
                    modifier = Modifier.fillMaxWidth()
                        .align(Alignment.TopCenter)
                        .background(Color.White)
                        .padding(5.dp)
                )
            }
        }
    }
}
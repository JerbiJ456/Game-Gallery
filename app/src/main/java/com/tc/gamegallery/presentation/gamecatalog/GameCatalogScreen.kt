package com.tc.gamegallery.presentation.gamecatalog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun gameCatalogScreen(
    viewModel: GameCatalogViewModel,
    navController: NavController,
    genres: String?,
    tags: String?,
) {

    val state by viewModel.state.collectAsState()
    LaunchedEffect(listOf(genres, tags)) {
        viewModel.getCallInfo(genres, tags)
    }

    val topPadding = if (genres != null || tags != null) 0.dp else 70.dp

    Surface (color = Color(0xFF1e293b)) {
        Box(modifier = Modifier.fillMaxSize()) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.padding(end = 16.dp, top = topPadding, start = 16.dp), // Pour ajouter un peu d'espace au bas de la liste
                    horizontalArrangement = Arrangement.spacedBy(8.dp) // espace entre les cartes sur l'axe horizontal
                ) {
                    itemsIndexed(state.results) { index, game ->
                        if(index == state.currentPage*10 - 1 && state.nextPage != null) {
                            viewModel.nextPage()
                        }
                        GameTile(
                            game,
                            navController = navController
                        )
                    }
                }
                if (genres == null && tags == null) {
                    TextField(
                        shape = RoundedCornerShape(5.dp),
                        value = state.currentSearch,
                        onValueChange = { viewModel.search(it) },
                        label = { Text("Search") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.TopCenter)
                            .background(Color.White)
                            .padding(5.dp)
                    )
                }
            }
        }
    }
}

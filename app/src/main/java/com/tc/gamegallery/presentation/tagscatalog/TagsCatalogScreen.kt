package com.tc.gamegallery.presentation.tagscatalog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun TagsCatalogScreen(
    viewModel: TagsCatalogViewModel,
    navController: NavController
) {
    val state by viewModel.state.collectAsState()
    Surface (color = Color(0xFF1e293b)) {
        Box(modifier = Modifier.fillMaxSize()) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.padding(end = 16.dp, start = 16.dp), // Pour ajouter un peu d'espace au bas de la liste
                    horizontalArrangement = Arrangement.spacedBy(8.dp) // espace entre les cartes sur l'axe horizontal
                ) {
                    itemsIndexed(state.results) { index, game ->
                        if(index == state.currentPage*10 - 1 && state.nextPage != null) {
                            viewModel.nextPage()
                        }
                        TagsTile(
                            game,
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}
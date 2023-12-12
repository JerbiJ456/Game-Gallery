package com.tc.gamegallery.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.tc.gamegallery.domain.GameCatalog
import kotlin.random.Random

@Composable
fun gameCatalogScreen(
    state: GameCatalogViewModel.GamesCatalogState,
    onSelectGame: (id: Int) -> Unit,
    onDismissGameDetails: () -> Unit,
    onNextPage: () -> Unit,
    onPreviousPage: () -> Unit,
    onSearch: (search: String) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.padding(16.dp),
                contentPadding = PaddingValues(bottom = 16.dp), // Pour ajouter un peu d'espace au bas de la liste
                horizontalArrangement = Arrangement.spacedBy(8.dp) // espace entre les cartes sur l'axe horizontal
            ) {
                items(state.gamesCatalog) { game -> gameItem(
                    game,
                    modifier = Modifier.clickable { onSelectGame(game.id) })
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .align(Alignment.BottomCenter)
                    .navigationBarsPadding()
                    .background(Color.White)
            ) {
                Button(
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp),
                    onClick = {
                        onPreviousPage()
                    }
                ) {
                    Text("Previous")
                }
                Text(
                    text = state.currentPage.toString(),
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                        .wrapContentWidth(Alignment.CenterHorizontally)
                        .wrapContentHeight(Alignment.CenterVertically)
                )
                Button(
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp),
                    onClick = {
                        onNextPage()
                    }
                ) {
                    Text("Next")
                }
            }
            TextField(
                value = state.currentSearch,
                onValueChange = { onSearch(it) },
                label = { Text("Search") },
                modifier = Modifier.fillMaxWidth()
                    .align(Alignment.TopCenter)
                    .background(Color.White)
            )
        }
    }
}

@Composable
private fun gameItem(
    game: GameCatalog,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(0.5f) // Prend 50% de la largeur de l'écran
            .padding(4.dp), // Petite marge pour éviter que les cartes ne se collent entre elles
        elevation = 4.dp
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clipToBounds()
            ) {
                SubcomposeAsyncImage(
                    model = game.thumbnailImage,
                    loading = {
                        CircularProgressIndicator()
                    },
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
            Text(
                text = game.name,
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(16.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
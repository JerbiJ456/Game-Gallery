package com.tc.gamegallery.presentation.gamecatalog

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
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
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.tc.gamegallery.presentation.GameGalleryViewModel

@Composable
fun gameCatalogScreen(
    viewModel: GameCatalogViewModel,
    navController: NavController,
    appViewModel: GameGalleryViewModel,
    genres: String?,
    tags: String?,
) {

    val state by viewModel.state.collectAsState()
    LaunchedEffect(listOf(genres, tags)) {
        viewModel.getCallInfo(genres, tags)
    }

    val scrollState = rememberLazyGridState()
    appViewModel.updateScrollPosition(scrollState.firstVisibleItemIndex)

    var sizeState by remember { mutableStateOf(0.dp) }
    val size by animateDpAsState(targetValue = sizeState,
        tween(
            durationMillis = 300,
            easing = LinearEasing
        ), label = ""
    )
    sizeState = if (appViewModel.isSearchOpen()) 55.dp else 0.dp

    Surface (color = Color(0xFF1e293b)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            if (genres == null && tags == null) {
                TextField(
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = ""
                        ) },
                    trailingIcon = {
                        if (state.currentSearch.isNotBlank()) {
                            Box(modifier = Modifier
                                .clickable { viewModel.onSearchTextChange("") })
                            {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = ""
                                )
                            }
                        }
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    value = state.currentSearch,
                    onValueChange = { viewModel.onSearchTextChange(it) },
                    placeholder = { Text("Search for games...") },
                    modifier = Modifier
                        .height(size)
                        .fillMaxWidth()
                        .background(Color.White),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.White,
                        focusedIndicatorColor =  Color.Transparent,
                        unfocusedIndicatorColor =  Color.Transparent)
                )
            }
            if (state.isLoading) {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            } else {
                LazyVerticalGrid(
                    state = scrollState,
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.padding(
                        end = 16.dp,
                        start = 16.dp
                    ), // Pour ajouter un peu d'espace au bas de la liste
                    horizontalArrangement = Arrangement.spacedBy(8.dp) // espace entre les cartes sur l'axe horizontal
                ) {
                    itemsIndexed(state.results) { index, game ->
                        if (index == state.currentPage * 10 - 1 && state.nextPage != null) {
                            viewModel.nextPage()
                        }
                        GameTile(
                            game,
                            navController = navController
                        )
                    }

                    item {
                        if (state.newPageIsLoading) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }
}

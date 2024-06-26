package com.tc.gamegallery.presentation.favoritecatalog


import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
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
import com.tc.gamegallery.presentation.GameGalleryViewModel
import com.tc.gamegallery.presentation.tabRowItems

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavoriteScreen(
    viewModel: FavoriteViewModel,
    appViewModel: GameGalleryViewModel,
    navController: NavController
) {
    val state by viewModel.state.collectAsState()

    val pagerState = rememberPagerState {
        tabRowItems.size
    }

    LaunchedEffect(pagerState.currentPage) {
        appViewModel.changeSelectedTabRow(pagerState.currentPage)
    }

    LaunchedEffect(state.results) {
        viewModel.updatePage()
    }

    LaunchedEffect(appViewModel.getSelectedTabRow()) {
        pagerState.animateScrollToPage(appViewModel.getSelectedTabRow())
    }

    val newReleasesScrollState = rememberLazyGridState()
    val scrollState = rememberLazyGridState()
    val upcomingReleasesScrollState = rememberLazyGridState()

    var sizeState by remember { mutableStateOf(0.dp) }
    val size by animateDpAsState(
        targetValue = sizeState,
        tween(
            durationMillis = 300,
            easing = LinearEasing
        ), label = ""
    )
    sizeState = if (appViewModel.isSearchOpen()) 55.dp else 0.dp

    Surface(color = Color(0xFF1e293b)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            if (state.isLoading) {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            } else {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxWidth()) { index ->
                    when (index) {
                        0 -> {
                            appViewModel.updateScrollPosition(scrollState.firstVisibleItemIndex)
                            LazyVerticalGrid(
                                state = scrollState,
                                columns = GridCells.Fixed(2),
                                modifier = Modifier.padding(
                                    end = 16.dp,
                                    start = 16.dp
                                ), // Pour ajouter un peu d'espace au bas de la liste
                                horizontalArrangement = Arrangement.spacedBy(8.dp) // espace entre les cartes sur l'axe horizontal
                            ) {
                                itemsIndexed(state.results) { _, game ->
                                    FavoriteTile(
                                        game,
                                        navController = navController,
                                        viewModel = viewModel
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
        }
    }
}
package com.tc.gamegallery.presentation

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

val tabRowItems = listOf(
    "All games",
    "Upcoming",
    "New releases",
)
@Composable
fun TopBar(
    navController: NavController,
    viewModel: GameGalleryViewModel,
    scrollUpState: State<Boolean?>
) {
    var TopBarSizeState by remember { mutableStateOf(57.dp) }
    val TopBarSize by animateDpAsState(targetValue = TopBarSizeState,
        tween(
            durationMillis = 300,
            easing = LinearEasing
        ), label = ""
    )
    TopBarSizeState = if (scrollUpState.value == false) 57.dp else 0.dp
    var TabRowSizeState by remember { mutableStateOf(40.dp) }
    val TabRowSize by animateDpAsState(targetValue = TabRowSizeState,
        tween(
            durationMillis = 300,
            easing = LinearEasing
        ), label = ""
    )
    TabRowSizeState = if (scrollUpState.value == false) 40.dp else 0.dp
    Surface(color = Color(0xFF1e293b)) {
        Column {
            TopAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(TopBarSize),
                contentColor = Color.White,
                title = {
                    Text(
                        text = viewModel.getCurrentActivity(),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                backgroundColor = Color(0xFF0f172a),
                navigationIcon = {
                    if (viewModel.shouldShowArrow()) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.Filled.ArrowBack, null)
                        }
                    }
                }, actions = {
                    if (viewModel.getCurrentActivity() == "Games") {
                        if (!viewModel.isSearchOpen()) {
                            IconButton(onClick = { viewModel.updateSearchOpen() }) {
                                Icon(Icons.Filled.Search, null)
                            }
                        } else {
                            IconButton(onClick = { viewModel.updateSearchOpen() }) {
                                Icon(Icons.Filled.SearchOff, null)
                            }
                        }
                    }
                }
            )
            if (viewModel.getCurrentActivity() == "Games") {
                TabRow(
                    selectedTabIndex = viewModel.getSelectedTabRow(),
                    backgroundColor = Color(0xFF0f172a),
                    contentColor = Color.White,
                    modifier = Modifier.height(TabRowSize)
                ) {
                    tabRowItems.forEachIndexed { index, item ->
                        Tab(
                            selected = index == viewModel.getSelectedTabRow(),
                            onClick = { viewModel.changeSelectedTabRow(index) },
                            text = { Text(item) },
                        )
                    }
                }
            }
        }
    }
}
package com.tc.gamegallery.presentation

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun TopBar(
    navController: NavController,
    viewModel: GameGalleryViewModel,
    scrollUpState: State<Boolean?>
) {
    var sizeState by remember { mutableStateOf(57.dp) }
    val size by animateDpAsState(targetValue = sizeState,
        tween(
            durationMillis = 300,
            easing = LinearEasing
        ), label = ""
    )
    sizeState = if (scrollUpState.value == false) 57.dp else 0.dp
    Surface(color = Color(0xFF1e293b)) {
            TopAppBar(
                modifier = Modifier.fillMaxWidth().height(size),
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
    }
}
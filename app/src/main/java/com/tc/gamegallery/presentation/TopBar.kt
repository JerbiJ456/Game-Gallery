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
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
    modifier: Modifier = Modifier,
    scrollUpState: State<Boolean?>
) {
    var sizeState by remember { mutableStateOf(70.dp) }
    val size by animateDpAsState(targetValue = sizeState,
        tween(
            durationMillis = 300,
            easing = LinearEasing
        ), label = ""
    )
    sizeState = if (scrollUpState.value == false) 70.dp else 0.dp
    Surface(
        color = Color(0xFF1e293b),
        elevation = 8.dp) {
        Box(
            modifier = modifier
                .background(
                    Color(0xFF0f172a)
                )
                .fillMaxWidth()
                .height(size)
        ) {
            Row(modifier = modifier.fillMaxHeight()) {
                if (viewModel.shouldShowArrow()) {
                    Box(modifier = modifier, contentAlignment = Alignment.TopStart) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier
                                .size(36.dp)
                                .offset(16.dp, 16.dp)
                                .clickable {
                                    navController.popBackStack()
                                }
                                .padding(3.dp)
                        )
                    }
                }
                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp, vertical = 15.dp)
                ) {
                    Text(
                        text = viewModel.getCurrentActivity(),
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}
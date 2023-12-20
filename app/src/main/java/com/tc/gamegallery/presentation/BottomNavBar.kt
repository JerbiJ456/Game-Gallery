package com.tc.gamegallery.presentation


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Games
import androidx.compose.material.icons.filled.Sell
import androidx.compose.material.icons.filled.ViewModule
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Games
import androidx.compose.material.icons.outlined.Sell
import androidx.compose.material.icons.outlined.ViewModule
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
)

val items = listOf(
    BottomNavigationItem(
        title = "Games",
        selectedIcon = Icons.Filled.Games,
        unselectedIcon = Icons.Outlined.Games,
    ),
    BottomNavigationItem(
        title = "Genres",
        selectedIcon = Icons.Filled.ViewModule,
        unselectedIcon = Icons.Outlined.ViewModule,
    ),
    BottomNavigationItem(
        title = "Tags",
        selectedIcon = Icons.Filled.Sell,
        unselectedIcon = Icons.Outlined.Sell,
    ),
    BottomNavigationItem(
        title = "Liked",
        selectedIcon = Icons.Filled.Favorite,
        unselectedIcon = Icons.Outlined.FavoriteBorder,
    ),
)

@Composable
fun BottomNavBar (navController: NavController) {
    val selectedItemIndex = remember {
        mutableIntStateOf(0)
    }
    NavigationBar (
        modifier = Modifier.height(70.dp),
        containerColor = Color(0xFF0f172a),
        tonalElevation = 1.dp){
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItemIndex.value == index,
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color(0xFF334155)
                ),
                onClick = {
                    selectedItemIndex.value = index
                    navController.navigate(item.title.lowercase())
                },
                icon = {
                    Box(
                        modifier = Modifier.size(width = 59.dp, height = 50.dp),
                        contentAlignment = Alignment.TopCenter) {
                        Icon(
                            imageVector = if (index == selectedItemIndex.value) {
                                item.selectedIcon
                            } else item.unselectedIcon,
                            tint = Color.White,
                            contentDescription = item.title,
                        )
                        Box(modifier = Modifier.height(50.dp), contentAlignment = Alignment.BottomCenter) {
                            Text(text = item.title, color=Color.White, fontWeight = if (index == selectedItemIndex.value) { FontWeight.Bold } else FontWeight.Normal)
                        }
                    }
                },
            )
        }
    }
}
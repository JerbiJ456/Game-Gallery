package com.tc.gamegallery.presentation.tagscatalog

import com.tc.gamegallery.domain.ResultGenresTags

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage

@Composable
fun TagsTile(
    tags: ResultGenresTags,
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    val games = tags.games.map { it.name }
    Card(
        modifier = modifier
            .height(230.dp)
            .fillMaxWidth(0.5f) // Prend 50% de la largeur de l'écran
            .padding(horizontal = 4.dp, vertical = 7.dp) // Petite marge pour éviter que les cartes ne se collent entre elles
            .clickable { navController.navigate("games?tags=${tags.id}&tagsName=${tags.name.replaceFirstChar(Char::titlecase)}") },
        elevation = 4.dp,
        shape = RoundedCornerShape(20.dp),
        backgroundColor = Color(0xFF334155),
        contentColor = Color(0xFFd1d5db),
    ) {
        Column (modifier = Modifier.fillMaxHeight()) {
            Box(
                modifier = modifier
                    .fillMaxHeight(0.58f)
                    .fillMaxWidth()
                    .clipToBounds(),
            ) {
                SubcomposeAsyncImage(
                    model = tags.thumbnailImage,
                    loading = {
                        CircularProgressIndicator(modifier = modifier
                            .align(Alignment.Center)
                            .fillMaxSize())
                    },
                    contentDescription = null,
                    modifier = modifier
                        .fillMaxSize(),
                    alignment = Alignment.TopCenter
                )
            }
            Text(
                text = tags.name.replaceFirstChar(Char::titlecase),
                modifier = modifier.padding(horizontal = 16.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp
            )
            Text(
                text = games.joinToString(", "),
                modifier.padding(horizontal = 16.dp, vertical = 6.dp),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Normal
            )
        }
    }
}
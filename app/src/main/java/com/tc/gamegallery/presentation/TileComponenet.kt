package com.tc.gamegallery.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.tc.gamegallery.domain.GameCatalog

@Composable
fun gameItem(
    game: GameCatalog,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(250.dp)
            .fillMaxWidth(0.5f) // Prend 50% de la largeur de l'écran
            .padding(horizontal = 4.dp, vertical = 7.dp), // Petite marge pour éviter que les cartes ne se collent entre elles
        elevation = 4.dp,
        shape = RoundedCornerShape(30.dp)
    ) {
        Column {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .clipToBounds(),
                contentAlignment = Alignment.TopCenter
            ) {
                SubcomposeAsyncImage(
                    model = game.thumbnailImage,
                    loading = {
                        CircularProgressIndicator()
                    },
                    contentDescription = null,
                    modifier = modifier
                        .fillMaxWidth()
                )
            }
            Text(
                text = game.name,
                modifier = modifier.padding(16.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
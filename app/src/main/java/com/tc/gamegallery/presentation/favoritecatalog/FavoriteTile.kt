package com.tc.gamegallery.presentation.favoritecatalog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.tc.gamegallery.R
import com.tc.gamegallery.domain.ResultGames

val platformsMap = mapOf(
    "playstation" to R.drawable.playstation,
    "pc" to R.drawable.pc,
    "xbox" to R.drawable.xbox,
    "ios" to R.drawable.ios,
    "android" to R.drawable.android,
    "mac" to R.drawable.mac,
    "linux" to R.drawable.linux,
    "nintendo" to R.drawable.nintendo,
    "web" to R.drawable.web,
    "sega" to R.drawable.sega
)

@Composable
fun FavoriteTile(
    game: ResultGames,
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: FavoriteViewModel
) {
    val genres = game.genres.map { it.name }
    Card(
        modifier = modifier
            .height(230.dp)
            .fillMaxWidth(0.5f) // Prend 50% de la largeur de l'écran
            .padding(
                horizontal = 4.dp,
                vertical = 7.dp
            ) // Petite marge pour éviter que les cartes ne se collent entre elles
            .clickable { navController.navigate("detail/${game.name}/${game.id}") },
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
                    model = game.thumbnailImage,
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
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd) // Aligner l'icône en haut à droite
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(Color.Gray.copy(alpha = 0.5f))
                        .wrapContentSize(align = Alignment.TopEnd)
                        .clickable {
                            viewModel.removeFavoriteGameId(game.id)
                        }
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.deleteicon),
                        contentDescription = "Delete",
                        modifier = Modifier
                            .padding(8.dp)
                            .size(28.dp)
                    )
                }
            }
            Text(
                text = game.name,
                modifier = modifier.padding(start = 16.dp, end = 16.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold,
            )
            Row (modifier = modifier
                .padding(start = 16.dp, top = 15.dp, bottom = 3.dp, end = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ){
                game.platforms.take(5).forEach {
                    Icon(
                        modifier = modifier
                            .size(13.dp),
                        imageVector = ImageVector.vectorResource(platformsMap[it.slug]!!),
                        contentDescription = null,
                    )
                }
            }
            Text(
                text = genres.joinToString(", "),
                modifier.padding(horizontal = 16.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Normal
            )
        }
    }
}
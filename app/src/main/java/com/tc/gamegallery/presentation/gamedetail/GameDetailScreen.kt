package com.tc.gamegallery.presentation.gamedetail


import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import com.tc.gamegallery.R
import com.tc.gamegallery.domain.EsrbRating
import com.tc.gamegallery.domain.ResultGameSeries
import com.tc.gamegallery.domain.Screenshot
import com.tc.gamegallery.domain.Store
import com.tc.gamegallery.presentation.GameGalleryViewModel
import com.tc.gamegallery.presentation.gamecatalog.GameTile
import com.tc.gamegallery.presentation.gamecatalog.platformsMap

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
fun GameDetailScreen(
    id: Int,
    detailsViewModal: GameDetailScreenViewModel,
    appViewModel: GameGalleryViewModel,
    navController: NavController
) {
    LaunchedEffect(id) { // only launch once
        detailsViewModal.updateGameId(id)
    }
    val detailState by detailsViewModal.state.collectAsState()
    val scrollState = rememberLazyListState()
    appViewModel.updateScrollPosition(scrollState.firstVisibleItemIndex)

    Surface (color = Color(0xFF1e293b), contentColor = Color(0xFFd1d5db)) {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 5.dp)) {
            if (detailState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                Box(modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize(Alignment.Center)
                    ) {
                        LazyColumn(state = scrollState) {
                            item {
                                Box(modifier = Modifier.fillMaxSize()) {
                                    TopImages(
                                        backgroundImage = detailState.gameDetails?.backgroundImage,
                                        iconImage = detailState.gameDetails?.thumbnailImage
                                    )
                                }
                            }
                            item { GameDetails(detailState = detailState) }
                            /*if (!detailState.gameSeries.results.isNullOrEmpty()) {
                                item {
                                    Text(
                                        text = detailState.cachedGameSeries.toString() + detailState.nextPage.toString(),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 17.sp,
                                        modifier = Modifier.padding(
                                            start = 15.dp,
                                            top = 14.dp,
                                            bottom = 10.dp
                                        )
                                    )
                                }
                            } else item { Text(
                                text = "oui",
                                fontWeight = FontWeight.Bold,
                                fontSize = 17.sp,
                                modifier = Modifier.padding(
                                    start = 15.dp,
                                    top = 14.dp,
                                    bottom = 10.dp
                                )
                            ) }*/
                            if (detailState.cachedGameSeries.isNotEmpty()) {
                                item {
                                    Text(
                                        text = "Other games in the series",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 17.sp,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        modifier = Modifier.padding(start = 15.dp, top = 14.dp, bottom = 10.dp)
                                    )
                                }
                                val itemCount = if(detailState.cachedGameSeries.size % 2 == 0) {
                                    detailState.cachedGameSeries.size / 2
                                } else {
                                    detailState.cachedGameSeries.size / 2 + 1
                                }
                                items(itemCount) {
                                    if (it >= itemCount - 1 && detailState.nextPage != null) {
                                        detailsViewModal.nextPage()
                                    }
                                    GamesRow(rowIndex = it, navController = navController, detailState = detailState)
                                }
                                /*itemsIndexed(detailState.cachedGameSeries) { index, game ->
                                    if (index == (detailState.currentPage) * 4 - 1 && detailState.nextPage != null) {
                                        detailsViewModal.nextPage()
                                    }
                                    GameSeriesTile(
                                        game,
                                        navController = navController
                                    )
                                }*/
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GamesRow(rowIndex: Int, navController: NavController, detailState: GameDetailScreenState) {
    Row (modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)){
        GameSeriesTile(
            game = detailState.cachedGameSeries[rowIndex * 2],
            navController = navController,
            rowElement = 0
        )
        if(detailState.cachedGameSeries.size >= rowIndex * 2 + 2) {
            GameSeriesTile(
                game = detailState.cachedGameSeries[rowIndex * 2 + 1],
                navController = navController,
                rowElement = 1
            )
        }
    }
}

@Composable
fun GameSeriesTile(game: ResultGameSeries, navController: NavController, rowElement: Int) {
    val genres = game.genres.map { it.name }
    Box {
        Card(
            modifier = Modifier
                .height(210.dp)
                .fillMaxWidth(if (rowElement == 1) 1f else 0.5f) // Prend 50% de la largeur de l'écran
                .padding(
                    start = if (rowElement == 1) 8.dp else 0.dp,
                    end = if (rowElement == 1) 0.dp else 8.dp,
                    bottom = 13.dp
                ) // Petite marge pour éviter que les cartes ne se collent entre elles
                .clickable { navController.navigate("detail/${game.name}/${game.id}") },
            elevation = 4.dp,
            shape = RoundedCornerShape(20.dp),
            backgroundColor = Color(0xFF334155),
            contentColor = Color(0xFFd1d5db),
        ) {
            Column(modifier = Modifier.fillMaxHeight()) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight(0.5f)
                        .fillMaxWidth()
                        .clipToBounds(),
                ) {
                    AsyncImage(
                        model = game.thumbnailImage,
                        contentScale = ContentScale.Crop,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize(),
                        alignment = Alignment.TopCenter
                    )
                }
                Text(
                    text = game.name,
                    modifier = Modifier.padding(start = 15.dp, end = 15.dp, top = 4.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold,
                )
                Row(
                    modifier = Modifier
                        .padding(start = 16.dp, top = 15.dp, bottom = 3.dp, end = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    game.platforms.take(5).forEach {
                        Icon(
                            modifier = Modifier
                                .size(15.dp),
                            imageVector = ImageVector.vectorResource(platformsMap[it.slug]!!),
                            contentDescription = null,
                        )
                    }
                }
                Text(
                    text = genres.joinToString(", "),
                    modifier = Modifier.padding(horizontal = 16.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}

@Composable
fun GameDetails(detailState: GameDetailScreenState) {
    var showWholeDescription by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Text(
            text = detailState.gameDetails?.name ?: "N/A",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 23.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        StoresSection(stores = detailState.gameDetails?.stores!!)
        GameInfo(
            esrb = detailState.gameDetails!!.esrbRating,
            metacritics = detailState.gameDetails?.metacritic.toString(),
            publishers = detailState.gameDetails?.publishers?.elementAt(0)?.name,
            genres = detailState.gameDetails?.genres?.elementAt(0)?.name
        )
        Text(
            text = "Screenshots",
            fontWeight = FontWeight.Bold,
            fontSize = 17.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(start = 15.dp, top = 8.dp, bottom = 10.dp),
        )
        ScreenshotsSection(screenshots = detailState.gameDetails?.screenshots)
        Text(
            text = "Description",
            fontWeight = FontWeight.Bold,
            fontSize = 17.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(start = 15.dp, top = 14.dp, bottom = 10.dp),
        )
        Box(modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF334155))
            .clickable { showWholeDescription = !showWholeDescription }
        ) {
            Text(
                text = detailState.gameDetails?.description ?: "No data",
                fontWeight = FontWeight.Bold,
                maxLines = if (showWholeDescription) 100 else 10,
                overflow = TextOverflow.Ellipsis,
                fontSize = 17.sp,
                modifier = Modifier.padding(
                    start = 15.dp,
                    top = 14.dp,
                    bottom = 10.dp,
                    end = 15.dp
                ),
            )
        }
    }
}

@Composable
fun StoresSection(stores: List<Store>) {
    if (stores.isNotEmpty()) {
        val scrollState = rememberScrollState()
        val ctx = LocalContext.current
        val shouldBeScrollable = stores.joinToString { it.name }.length >= 43
        Box(modifier = Modifier
            .padding(horizontal = 5.dp)) {
            Row(
                modifier = if (shouldBeScrollable) Modifier
                    .horizontalScroll(scrollState)
                    .padding(top = 10.dp, bottom = 3.dp, start = 5.dp, end = 5.dp)
                    .fillMaxWidth()
                    .align(Center) else Modifier
                    .padding(top = 10.dp, bottom = 3.dp, start = 5.dp, end = 5.dp)
                    .fillMaxWidth()
                    .align(Center),
                horizontalArrangement = if (shouldBeScrollable) Arrangement.spacedBy(space = 6.dp) else Arrangement.Center
            ) {
                stores.forEach { store ->
                    Box (modifier = Modifier.padding(end = if (shouldBeScrollable) 0.dp else 6.dp)) {
                        Box(
                            modifier = Modifier
                                .border(
                                    width = 1.dp,
                                    color = Color(0xFF3ABFF8),
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .wrapContentSize()
                                .clickable {
                                    ctx.startActivity(
                                        Intent(
                                            Intent.ACTION_VIEW,
                                            Uri.parse("https://www." + store.domain)
                                        )
                                    )
                                }
                        ) {
                            Text(
                                text = store.name.uppercase(),
                                color = Color(0xFF3ABFF8),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(5.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ScreenshotsSection(screenshots: List<Screenshot>?) {
    Box (modifier = Modifier
        .height(180.dp)
        .padding(horizontal = 15.dp)) {
        LazyRow(
            modifier = Modifier
                .fillMaxHeight(),
            userScrollEnabled = true,
            horizontalArrangement = Arrangement.spacedBy(space = 15.dp),
        ) {
            items(screenshots!!) { screenshot ->
                AsyncImage(
                    model = screenshot.image,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxHeight(),
                    contentDescription = null,
                )
            }
        }
    }
}

@Composable
fun TopImages(backgroundImage: String?, iconImage: String?) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 10.dp),
            ) {
                AsyncImage(
                    model = backgroundImage,
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    modifier = Modifier
                        .size(width = 700.dp, height = 240.dp),
                    alignment = Alignment.TopCenter
                )
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .padding(top = 170.dp)
                ) {
                    AsyncImage(
                        model = iconImage,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(105.dp)
                            .clip(CircleShape)
                            .align(Alignment.TopCenter),
                        contentDescription = null,
                    )
                }
            }
        }
    }
}

@Composable
fun GameInfo(esrb: EsrbRating = EsrbRating(), genres: String? = null, publishers: String? = null, metacritics: String? = null) {
    val genre = genres ?: "N/A"
    val publisher = publishers ?: "N/A"
    val metacritic = metacritics ?: "N/A"
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(70.dp)
        .padding(10.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(modifier = Modifier.padding(bottom = 5.dp), text = "ESRB", fontSize = 15.sp,)
                    Text(text = esrb.name,
                        fontSize = 15.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis)
                }
            }
            Divider(
                color = Color(0xFFd1d5db),
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(modifier = Modifier.padding(bottom = 5.dp), text = "Genres", fontSize = 15.sp)
                    Text(text = genre,
                        fontSize = 15.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis)
                }
            }
            Divider(
                color = Color(0xFFd1d5db),
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(modifier = Modifier.padding(bottom = 5.dp), text = "Publishers", fontSize = 15.sp,)
                    Text(modifier = Modifier.padding(horizontal = 7.dp), text = publisher,
                        fontSize = 15.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis)
                }
            }
            Divider(
                color = Color(0xFFd1d5db),
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(modifier = Modifier.padding(bottom = 5.dp), text = "Metacrtitics", fontSize = 15.sp)
                    Text(text = metacritic,
                        fontSize = 15.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis)
                }
            }
        }
    }
}
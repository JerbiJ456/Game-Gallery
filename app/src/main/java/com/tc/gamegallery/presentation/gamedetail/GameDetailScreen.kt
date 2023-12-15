package com.tc.gamegallery.presentation.gamedetail


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.tc.gamegallery.domain.EsrbRating
import com.tc.gamegallery.domain.Screenshot

@Composable
fun GameDetailScreen(
    id: Int,
    detailsViewModal: GameDetailScreenViewModel,
) {

    LaunchedEffect(id) { // only launch once
        detailsViewModal.updateGameId(id)
    }
    val detailState by detailsViewModal.state.collectAsState()
    val scrollState = rememberScrollState()
    var showWholeDescription by remember {
        mutableStateOf(false)
    }

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
                            Column(
                                modifier = Modifier
                                    .verticalScroll(scrollState)
                                    .fillMaxWidth(),
                            ) {
                                Box(modifier = Modifier.fillMaxSize()) {
                                    TopImages(
                                        backgroundImage = detailState.gameDetails?.backgroundImage,
                                        iconImage = detailState.gameDetails?.thumbnailImage
                                    )
                                }
                                Text(
                                    text = detailState.gameDetails?.name ?: "N/A",
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.align(Alignment.CenterHorizontally),
                                    fontSize = 23.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                )
                                GameInfo(
                                    esrb = detailState.gameDetails!!.esrbRating,
                                    metacritics = detailState.gameDetails?.metacritic.toString(),
                                    publishers = detailState.gameDetails?.publishers!!.elementAt(0).name,
                                    genres = detailState.gameDetails?.genres!!.elementAt(0).name)
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
                                    .fillMaxHeight()
                                    .background(color = Color(0xFF334155))
                                    .clickable { showWholeDescription = !showWholeDescription }
                                    ) {
                                    Text(
                                        text = detailState.gameDetails?.description ?: "No data",
                                        fontWeight = FontWeight.Bold,
                                        maxLines = if (showWholeDescription) 100 else 10,
                                        overflow = TextOverflow.Ellipsis,
                                        fontSize = 17.sp,
                                        modifier = Modifier.padding(start = 15.dp, top = 14.dp, bottom = 10.dp, end = 15.dp),
                                    )
                                }
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
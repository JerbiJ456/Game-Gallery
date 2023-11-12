package com.tc.gamegallery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.tc.gamegallery.presentation.GameCatalogViewModel
import com.tc.gamegallery.presentation.gameCatalogScreen
import com.tc.gamegallery.ui.theme.GameGalleryTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GameGalleryTheme {
                val viewModel = hiltViewModel<GameCatalogViewModel>()
                val state by viewModel.state.collectAsState()

                gameCatalogScreen(
                    state = state,
                    onSelectGame = { viewModel.selectGame(it) },
                    onDismissGameDetails = {},
                    onNextPage = { viewModel.nextPage() },
                    onPreviousPage = { viewModel.previousPage() },
                    onSearch = { viewModel.search(it.toString()) }
                )
            }
        }
    }
}
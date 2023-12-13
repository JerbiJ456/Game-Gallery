package com.tc.gamegallery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.tc.gamegallery.presentation.GameGalleryScreen
import com.tc.gamegallery.ui.theme.GameGalleryTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GameGalleryTheme {
                GameGalleryScreen()
            }
        }
    }
}

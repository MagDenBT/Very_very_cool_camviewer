package ch.magden.veryverycoolcamviewer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStore
import ch.magden.veryverycoolcamviewer.presentations.MainPager
import ch.magden.veryverycoolcamviewer.presentations.cameras.CamerasViewModel
import ch.magden.veryverycoolcamviewer.ui.theme.AppTheme
import ch.magden.veryverycoolcamviewer.utils.viewModelCreator

class MainActivity : ComponentActivity() {

    private val camerasViewModel by viewModelCreator { CamerasViewModel() }
    private val doorphonesViewModel by viewModelCreator { CamerasViewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModels = listOf(camerasViewModel, doorphonesViewModel)

        setContent {
            AppTheme {
                MainPager(viewModels)
            }
        }
    }
}

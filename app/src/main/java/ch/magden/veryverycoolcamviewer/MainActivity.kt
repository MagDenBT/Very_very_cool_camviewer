package ch.magden.veryverycoolcamviewer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import ch.magden.veryverycoolcamviewer.core.utils.viewModelCreator
import ch.magden.veryverycoolcamviewer.ui.screens.MainPager
import ch.magden.veryverycoolcamviewer.ui.theme.AppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val mainActivityComponent = (application as InitApp).appComponent.mainActivityComponent().create()
        super.onCreate(savedInstanceState)

        val camerasViewModel by viewModelCreator { mainActivityComponent.camerasViewModel() }
        val doorphonesViewModel by viewModelCreator { mainActivityComponent.doorphonesViewModel() }

        val viewModels = listOf(camerasViewModel, doorphonesViewModel)

        setContent {
            AppTheme {
                MainPager(viewModels)
            }
        }
    }
}

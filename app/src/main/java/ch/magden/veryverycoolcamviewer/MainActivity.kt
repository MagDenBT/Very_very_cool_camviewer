package ch.magden.veryverycoolcamviewer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import ch.magden.veryverycoolcamviewer.presentations.MainPager
import ch.magden.veryverycoolcamviewer.presentations.cameras.CamerasViewModel
import ch.magden.veryverycoolcamviewer.presentations.doorphones.DoorphonesViewModel
import ch.magden.veryverycoolcamviewer.ui.theme.AppTheme
import ch.magden.veryverycoolcamviewer.utils.viewModelCreator

class MainActivity : ComponentActivity() {

    private lateinit var camerasViewModel: CamerasViewModel
    private lateinit var doorphonesViewModel: DoorphonesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        Repositories.init(applicationContext)
        super.onCreate(savedInstanceState)

        camerasViewModel = viewModelCreator { CamerasViewModel(Repositories.dataRepository) }.value
        doorphonesViewModel =
            viewModelCreator { DoorphonesViewModel(Repositories.dataRepository) }.value

        val viewModels = listOf(camerasViewModel, doorphonesViewModel)

        setContent {
            AppTheme {
                MainPager(viewModels)
            }
        }
    }
}

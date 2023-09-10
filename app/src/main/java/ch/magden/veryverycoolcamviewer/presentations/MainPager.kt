package ch.magden.veryverycoolcamviewer.presentations

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.ViewModel
import ch.magden.veryverycoolcamviewer.R
import ch.magden.veryverycoolcamviewer.presentations.cameras.CamerasViewModel
import ch.magden.veryverycoolcamviewer.presentations.cameras.PreloadCamerasScreen
import ch.magden.veryverycoolcamviewer.presentations.doorphones.DoorphonesViewModel
import ch.magden.veryverycoolcamviewer.presentations.doorphones.PreloadDoorphonesScreen
import ch.magden.veryverycoolcamviewer.ui.theme.appHeaderTextStyle
import ch.magden.veryverycoolcamviewer.ui.theme.blue
import ch.magden.veryverycoolcamviewer.ui.theme.tabTextStyle
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Composable
fun <T : ViewModel> MainPager(viewModels: List<T>) {
    val composeScreens = getComposeScreens(viewModels)
    val pagerState = rememberPagerState()

    Scaffold(topBar = { TopBar() }, contentWindowInsets = WindowInsets(0.dp)) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            Tabs(composeScreens, pagerState, Modifier.height(44.dp))
            TabContent(composeScreens, pagerState)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = stringResource(R.string.app_bar_title),
                style = appHeaderTextStyle
            )
        }
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun Tabs(
    composeScreens: List<ComposeScreen>,
    pagerState: PagerState,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()

    TabRow(
        selectedTabIndex = pagerState.currentPage,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                color = blue,
                height = 2.dp,
                modifier = Modifier
                    .tabIndicatorOffset(tabPositions[pagerState.currentPage])
            )
        }
    ) {
        composeScreens.forEachIndexed { index: Int, composeScreen: ComposeScreen ->
            Tab(
                modifier = Modifier.zIndex(2f),
                selected = index == pagerState.currentPage,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }

            ) {
                Text(
                    text = composeScreen.title,
                    style = tabTextStyle,
                    modifier = modifier.height(44.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun TabContent(composeScreens: List<ComposeScreen>, pagerState: PagerState) {
    HorizontalPager(
        count = composeScreens.size,
        state = pagerState,
        verticalAlignment = Alignment.Top
    ) { page ->
        composeScreens[page].screen(composeScreens[page].viewModel)
    }
}

@Composable
private fun <T : ViewModel> getComposeScreens(
    viewModels: List<T>
): List<ComposeScreen> {
    val composeScreens = mutableListOf<ComposeScreen>()

    viewModels.forEach { viewModel ->
        when (viewModel) {
            is CamerasViewModel -> {
                composeScreens.add(
                    ComposeScreen(
                        title = stringResource(R.string.cameras_screen_title),
                        screen = { vm -> PreloadCamerasScreen(vm as CamerasViewModel) },
                        viewModel = viewModel
                    )
                )
            }

            is DoorphonesViewModel -> {
                composeScreens.add(
                    ComposeScreen(
                        title = stringResource(R.string.doorphones_screen_title),
                        screen = { vm -> PreloadDoorphonesScreen(vm as DoorphonesViewModel) },
                        viewModel = viewModel
                    )
                )
            }
        }
    }

    return composeScreens
}

typealias ComposeFun = @Composable (viewModel: ViewModel) -> Unit

data class ComposeScreen(val title: String, val screen: ComposeFun, val viewModel: ViewModel)

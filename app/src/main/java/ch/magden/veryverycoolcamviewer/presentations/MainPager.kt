package ch.magden.veryverycoolcamviewer.presentations

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.ViewModel
import ch.magden.veryverycoolcamviewer.R
import ch.magden.veryverycoolcamviewer.presentations.cameras.CamerasViewModel
import ch.magden.veryverycoolcamviewer.presentations.cameras.PreloadCamerasScreen
import ch.magden.veryverycoolcamviewer.presentations.doorphones.DoorphonesViewModel
import ch.magden.veryverycoolcamviewer.presentations.doorphones.PreloadDoorphonesScreen
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
                stringResource(R.string.app_bar_title),
            )
        },
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun Tabs(
    composeScreens: List<ComposeScreen>,
    pagerState: PagerState,
    modifier: Modifier = Modifier,
) {
    val scope = rememberCoroutineScope()
    val cornerRadius = 10.dp
//        val indicator = @Composable { tabPositions: List<TabPosition> ->
//            CustomIndicator(tabPositions, pagerState, cornerRadius)
//        }

    TabRow(
        selectedTabIndex = pagerState.currentPage,
        modifier = Modifier.clip(
            RoundedCornerShape(
                bottomEnd = cornerRadius,
                bottomStart = cornerRadius,
            ),
        ),

        ) {
        composeScreens.forEachIndexed { index: Int, composeScreen: ComposeScreen ->
            Tab(
                modifier = Modifier.zIndex(2f),
                selected = index == pagerState.currentPage,
//                    selectedContentColor = white,
//                    unselectedContentColor = black,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
            ) {
                Text(
                    text = composeScreen.title,
                    modifier = modifier.wrapContentHeight(Alignment.CenterVertically),
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
        verticalAlignment = Alignment.Top,
    ) { page ->
        composeScreens[page].screen
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
                composeScreens.add(ComposeScreen(
                    title = stringResource(R.string.cameras_screen_title),
                    screen = { vm -> PreloadCamerasScreen(vm as CamerasViewModel) }
                ))
            }
            is DoorphonesViewModel ->{
                composeScreens.add(ComposeScreen(
                    title = stringResource(R.string.doorphones_screen_title),
                    screen = { vm -> PreloadDoorphonesScreen(vm as DoorphonesViewModel) }
                ))
            }
        }


    }

    return composeScreens

}


typealias ComposeFun = @Composable (viewModel: ViewModel) -> Unit

data class ComposeScreen(val title: String, val screen: ComposeFun)


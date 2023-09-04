package ch.magden.veryverycoolcamviewer.presentations.doorphones

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ch.magden.veryverycoolcamviewer.R
import ch.magden.veryverycoolcamviewer.presentations.cameraItemSnapshot
import ch.magden.veryverycoolcamviewer.presentations.cameraItems
import ch.magden.veryverycoolcamviewer.presentations.cameras.CameraItem

import ch.magden.veryverycoolcamviewer.presentations.cameras.CamerasScreen
import ch.magden.veryverycoolcamviewer.presentations.cameras.CamerasViewModel

import ch.magden.veryverycoolcamviewer.presentations.doorphoneItemSnapshot
import ch.magden.veryverycoolcamviewer.presentations.doorphonesItems
import ch.magden.veryverycoolcamviewer.ui.theme.AppTheme
import ch.magden.veryverycoolcamviewer.ui.theme.extraSmall
import ch.magden.veryverycoolcamviewer.ui.theme.large
import ch.magden.veryverycoolcamviewer.ui.theme.micro
import ch.magden.veryverycoolcamviewer.ui.theme.small
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest

@Composable
fun DoorphonesScreen(
    viewModel: DoorphonesViewModel? = null,
    testDoorphonesItemsMap: SnapshotStateList<DoorphoneItem>? = null
) {

    val doorphonesItems = testDoorphonesItemsMap!!
    LazyColumn(
        modifier = Modifier.padding(
            top = 5.dp, start = large, end = large
        ), verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(items = doorphonesItems,
            key = { doorphoneItem -> doorphoneItem.id }) { doorphoneItem ->
            Spacer(Modifier.height(small))
            DoorphoneTile(modifier = Modifier.width(333.dp)
                .wrapContentHeight(),doorphoneItem = doorphoneItem)
        }

    }


}


@Composable
private fun DoorphoneTile(
    modifier: Modifier = Modifier, doorphoneItem: DoorphoneItem, isTest: Boolean = false
) {

    Card(
        modifier = modifier,
    ) {
        val hasSnapshot = !doorphoneItem.snapshotUrl.value.isNullOrBlank()

        Column {
            if (hasSnapshot) {
                Box(
                    modifier = Modifier
                        .height(207.dp)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                ) {


                    val painter =
                        if (isTest) painterResource(R.drawable.prev_snapshot) else rememberAsyncImagePainter(
                            ImageRequest.Builder(LocalContext.current)
                                .data(doorphoneItem.snapshotUrl.value).crossfade(true).build()
                        )
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = painter,
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                    Image(
                        modifier = Modifier.align(Alignment.Center),
                        painter = painterResource(id = R.drawable.ic_play),
                        contentScale = ContentScale.None,
                        contentDescription = null
                    )


                    val isFavoriteVisibility = if (doorphoneItem.isFavorite.value) 1f else 0f
                    Image(
                        modifier = Modifier
                            .alpha(isFavoriteVisibility)
                            .padding(micro)
                            .align(Alignment.TopEnd),
                        painter = painterResource(id = R.drawable.ic_is_favorite),
                        contentScale = ContentScale.None,
                        contentDescription = null
                    )
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(end = 24.72.dp, start = 16.48.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val columnPadding = if(hasSnapshot) Modifier.padding(top = 14.dp, bottom = 24.dp) else Modifier.padding(top = 22.dp, bottom = 30.dp)
            Column(modifier = columnPadding) {
                Text(text = doorphoneItem.name.value)
                
                if (doorphoneItem.online.value) {
                    Text(
                        text = stringResource(id = R.string.online_text)
                    )
                }
            }

            Image(modifier = Modifier.align(Alignment.CenterVertically),
                painter = painterResource(id = R.drawable.ic_lock),
                contentDescription = null,
                contentScale = ContentScale.None,)
        }

    }
}


@Preview(widthDp = 375, heightDp = 557, showBackground = true)
@Composable
private fun PrevScreen() {
    AppTheme {
        DoorphonesScreen(viewModel = null, doorphonesItems())
    }
}

@Preview(
    widthDp = 375, heightDp = 557, showBackground = true, backgroundColor = 0xFF00FF00,
    name = "Ret", uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL,
)
@Composable
private fun PrevDoorphoneTile() {
    AppTheme {
        DoorphoneTile(
            modifier = Modifier.width(333.dp)
                .wrapContentHeight().padding(horizontal = large),
            doorphoneItem = doorphoneItemSnapshot,
            isTest = true
        )
    }
}


data class DoorphoneItem(
    val id: Int,
    val name: MutableState<String>,
    val room: MutableState<String?>,
    val snapshotUrl: MutableState<String?>,
    val isFavorite: MutableState<Boolean>,
    val online: MutableState<Boolean> = mutableStateOf(!snapshotUrl.value.isNullOrBlank())
)



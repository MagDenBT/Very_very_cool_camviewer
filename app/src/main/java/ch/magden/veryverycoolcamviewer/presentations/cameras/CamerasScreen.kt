package ch.magden.veryverycoolcamviewer.presentations.cameras

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import ch.magden.veryverycoolcamviewer.R
import ch.magden.veryverycoolcamviewer.presentations.ActionsRow
import ch.magden.veryverycoolcamviewer.presentations.cameraItemSnapshot
import ch.magden.veryverycoolcamviewer.presentations.cameraItems
import ch.magden.veryverycoolcamviewer.ui.theme.ACTION_ITEM_SIDE_PADDING
import ch.magden.veryverycoolcamviewer.ui.theme.ACTION_ITEM_WIDTH
import ch.magden.veryverycoolcamviewer.ui.theme.ANIMATION_DURATION
import ch.magden.veryverycoolcamviewer.ui.theme.AppTheme
import ch.magden.veryverycoolcamviewer.ui.theme.CAMERA_CARD_OFFSET
import ch.magden.veryverycoolcamviewer.ui.theme.DOORPHONE_CARD_OFFSET
import ch.magden.veryverycoolcamviewer.ui.theme.MIN_DRAG_AMOUNT
import ch.magden.veryverycoolcamviewer.ui.theme.extraMedium
import ch.magden.veryverycoolcamviewer.ui.theme.extraSmall
import ch.magden.veryverycoolcamviewer.ui.theme.large
import ch.magden.veryverycoolcamviewer.ui.theme.micro
import ch.magden.veryverycoolcamviewer.ui.theme.roomTitle
import ch.magden.veryverycoolcamviewer.ui.theme.small
import ch.magden.veryverycoolcamviewer.utils.regardingDp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import kotlin.math.roundToInt

@Composable
fun CamerasScreen(
    viewModel: CamerasViewModel? = null,
    testCamerasItemsMap: SnapshotStateMap<MutableState<String>, SnapshotStateList<CameraItem>>? = null
) {

    val camerasItemsMap = testCamerasItemsMap!!
    val noHasRoomStub = stringResource(R.string.no_has_room)
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = extraSmall, start = large, end = large
            ),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        camerasItemsMap.keys.forEach { titleKey ->

            val camerasItems = camerasItemsMap[titleKey]
            if (camerasItems?.isNotEmpty() == true) {

                val titleText =
                    if (titleKey.value.isNullOrBlank()) noHasRoomStub else titleKey.value

                item {
                    RoomTitle(modifier = Modifier.padding(top = extraSmall), titleText = titleText)
                    Spacer(Modifier.height(1.dp))
                }



                items(items = camerasItems, key = { cameraItem -> cameraItem.id }) { cameraItem ->
                    CameraTile(   modifier = Modifier
                        .width(333.dp)
                        .wrapContentHeight(), cameraItem = cameraItem)
                }
            }

        }


    }


}

@Composable
private fun CameraTile(cameraItem: CameraItem, modifier: Modifier = Modifier, isTest: Boolean = false,) {
    Spacer(Modifier.height(small))
    Box(
        modifier = modifier, contentAlignment = Alignment.CenterEnd
    ) {

        var isRevealed by rememberSaveable {
            mutableStateOf(false)
        }


        val onFavorite = {
            cameraItem.isFavorite.value = !cameraItem.isFavorite.value
            isRevealed = !isRevealed
        }

        ActionsRow(
            actionIconWidth = ACTION_ITEM_WIDTH,
            actionIconSidePadding = ACTION_ITEM_SIDE_PADDING,
            onFavorite = onFavorite,
            isFavorite = cameraItem.isFavorite.value
        )

    CameraDraggebleCard(
        cameraItem = cameraItem,
        isRevealed = isRevealed,
        onExpand = { isRevealed = true },
        onCollapse = { isRevealed = false },
        isTest = isTest
    )
}
}

@Composable
private fun RoomTitle(modifier: Modifier = Modifier, titleText: String) {
    Text(
        modifier = modifier.fillMaxWidth(),
        text = titleText,
        style = roomTitle,
        textAlign = TextAlign.Start
    )
}


@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
private fun CameraDraggebleCard(
    modifier: Modifier = Modifier, cameraItem: CameraItem,
    isRevealed: Boolean,
    onExpand: () -> Unit,
    onCollapse: () -> Unit,
    isTest: Boolean = false
) {
    val transitionState = remember {
        MutableTransitionState(isRevealed).apply {
            targetState = !isRevealed
        }
    }
    val transition = updateTransition(transitionState, "cardTransition")

    val offsetTransition by transition.animateFloat(
        label = "cardOffsetTransition",
        transitionSpec = { tween(durationMillis = ANIMATION_DURATION) },
        targetValueByState = { if (isRevealed) -CAMERA_CARD_OFFSET.regardingDp() else 0f },
    )

    val defaultElevation by transition.animateDp(label = "cardElevation",
        transitionSpec = { tween(durationMillis = ANIMATION_DURATION) },
        targetValueByState = { if (isRevealed) 20.dp else 2.dp })

    Card(modifier = modifier
        .offset { IntOffset(offsetTransition.roundToInt(), 0) }
        .pointerInput(Unit) {
            detectTapGestures(onLongPress = { onExpand() }, onDoubleTap = { onExpand() })
        }
        .pointerInput(Unit) {
            detectHorizontalDragGestures { _, dragAmount ->
                when {
                    dragAmount >= MIN_DRAG_AMOUNT -> onCollapse()
                    dragAmount < -MIN_DRAG_AMOUNT -> onExpand()
                }
            }

        }, elevation = CardDefaults.cardElevation(
        defaultElevation = defaultElevation
    )
    ) {
        Column {
            if (!cameraItem.snapshotUrl.value.isNullOrBlank()) {
                Box(
                    modifier = Modifier
                        .height(207.dp)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                ) {
                    val painter =
                        if (isTest) painterResource(R.drawable.prev_snapshot) else rememberAsyncImagePainter(
                            ImageRequest.Builder(LocalContext.current)
                                .data(cameraItem.snapshotUrl.value).crossfade(true).build()
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

                    val isRecordingVisibility = if (cameraItem.isRecording.value) 1f else 0f
                    Image(
                        modifier = Modifier
                            .alpha(isRecordingVisibility)
                            .padding(extraSmall)
                            .align(Alignment.TopStart),
                        painter = painterResource(id = R.drawable.ic_recording),
                        contentScale = ContentScale.None,
                        contentDescription = null
                    )

                    val isFavoriteVisibility = if (cameraItem.isFavorite.value) 1f else 0f
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

            Text(
                text = cameraItem.name.value,
                modifier = Modifier.padding(start = extraMedium, top = 22.dp, bottom = 30.dp),
                textAlign = TextAlign.Start
            )
        }
    }
}

@Preview(widthDp = 375, heightDp = 557, showBackground = true)
@Composable
private fun PrevScreen() {
    AppTheme {
        CamerasScreen(viewModel = null, cameraItems())
    }
}

@Preview(
    widthDp = 375, heightDp = 557, showBackground = true, backgroundColor = 0xFF00FF00,
    name = "Ret", uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL,
)
@Composable
private fun PrevCameraTile() {
    AppTheme {
        CameraTile(
            modifier = Modifier
                .width(333.dp)
                .wrapContentHeight()
                .padding(horizontal = large),
            cameraItem = cameraItemSnapshot,
            isTest = true
        )
    }
}

@Preview(
    widthDp = 375, heightDp = 557, showBackground = true, backgroundColor = 0xFF00FF00,
    name = "Ret2", uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL,
)
@Composable
private fun PrevRoomTitle() {
    AppTheme {
        RoomTitle(titleText = "Гостиная")
    }
}


data class CameraItem(
    val id: Int,
    val name: MutableState<String>,
    val snapshotUrl: MutableState<String?>,
    val isFavorite: MutableState<Boolean>,
    val isRecording: MutableState<Boolean>
)

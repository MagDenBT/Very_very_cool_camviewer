package ch.magden.veryverycoolcamviewer.ui.screens

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ch.magden.veryverycoolcamviewer.R
import ch.magden.veryverycoolcamviewer.core.domainmodels.Camera
import ch.magden.veryverycoolcamviewer.ui.theme.AppTheme
import ch.magden.veryverycoolcamviewer.ui.theme.roomTitleTextStyle
import ch.magden.veryverycoolcamviewer.core.utils.Resource
import ch.magden.veryverycoolcamviewer.core.utils.regardingDp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import kotlin.math.roundToInt

val CAMERA_CARD_OFFSET = ACTION_ITEM_WIDTH.value + ACTION_ITEM_SIDE_PADDING.value

@Composable
fun PreloadCamerasScreen(viewModel: CamerasViewModel) {
    val camerasItemsMap by viewModel.roomsAndCameras.collectAsStateWithLifecycle()
    when (camerasItemsMap) {
        is Resource.Success -> CamerasScreen(
            camerasItemsMap = camerasItemsMap.data!!,
            onFavorite = { camera -> viewModel.switchCameraIsFavorite(camera) }
        )

        is Resource.Loading -> LoadingScreen()
        is Resource.Error -> LoadingScreen() // по-хорошему нужная отдельная логика отработки ошибок
    }
}

@Composable
private fun CamerasScreen(
    camerasItemsMap: Map<String?, List<Camera>>,
    onFavorite: (Camera) -> Unit
) {
    val noHasRoomStub = stringResource(R.string.no_has_room)
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = 8.dp,
                start = 21.dp,
                end = 21.dp
            ),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        camerasItemsMap.keys.forEach { titleKey ->

            val camerasItems = camerasItemsMap[titleKey]
            if (camerasItems?.isNotEmpty() == true) {
                val titleText = if (titleKey.isNullOrBlank()) noHasRoomStub else titleKey

                item {
                    RoomTitle(modifier = Modifier.padding(top = 8.dp), titleText = titleText)
                    Spacer(Modifier.height(1.dp))
                }

                items(items = camerasItems, key = { cameraItem -> cameraItem.id }) { cameraItem ->
                    var showActions by rememberSaveable {
                        mutableStateOf(false)
                    }

                    Spacer(Modifier.height(11.dp))

                    CameraTile(
                        modifier = Modifier.padding(bottom = 3.dp)
                            .width(333.dp)
                            .wrapContentHeight(),
                        camera = cameraItem,
                        onFavorite = { onFavorite(it) },
                        isShowingActions = showActions,
                        setShowActions = { showActions = it }
                    )
                }
            }
        }
    }
}

@Composable
private fun CameraTile(
    camera: Camera,
    onFavorite: (Camera) -> Unit,
    setShowActions: (Boolean) -> Unit,
    isShowingActions: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.CenterEnd
    ) {
        ActionsRow(
            actionIconWidth = ACTION_ITEM_WIDTH,
            actionIconSidePadding = ACTION_ITEM_SIDE_PADDING,
            onFavorite = {
                onFavorite(camera)
                setShowActions(false)
            },
            isFavorite = camera.isFavorite
        )

        CameraDraggebleCard(
            camera = camera,
            isShowingActions = isShowingActions,
            setShowActions = setShowActions
        )
    }
}

@Composable
private fun RoomTitle(modifier: Modifier = Modifier, titleText: String) {
    Text(
        modifier = modifier.fillMaxWidth(),
        text = titleText,
        style = roomTitleTextStyle,
        textAlign = TextAlign.Start
    )
}

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
private fun CameraDraggebleCard(
    camera: Camera,
    isShowingActions: Boolean,
    setShowActions: (Boolean) -> Unit
) {
    val transitionState = remember {
        MutableTransitionState(isShowingActions).apply {
            targetState = !isShowingActions
        }
    }
    val transition = updateTransition(transitionState, "cardTransition")

    val offsetTransition by transition.animateFloat(
        label = "cardOffsetTransition",
        transitionSpec = { tween(durationMillis = ANIMATION_DURATION) },
        targetValueByState = { if (isShowingActions) -CAMERA_CARD_OFFSET.regardingDp() else 0f }
    )

    val defaultElevation by transition.animateDp(
        label = "cardElevation",
        transitionSpec = { tween(durationMillis = ANIMATION_DURATION) },
        targetValueByState = { if (isShowingActions) 20.dp else 2.dp }
    )

    Card(
        modifier = Modifier
            .offset { IntOffset(offsetTransition.roundToInt(), 0) }
            .pointerInput(Unit) {
                detectTapGestures(onLongPress = {
                    setShowActions(!isShowingActions)
                }, onDoubleTap = { setShowActions(!isShowingActions) })
            }
            .pointerInput(Unit) {
                detectHorizontalDragGestures { _, dragAmount ->
                    when {
                        dragAmount >= MIN_DRAG_AMOUNT -> setShowActions(false)
                        dragAmount < -MIN_DRAG_AMOUNT -> setShowActions(true)
                    }
                }
            },
        elevation = CardDefaults.cardElevation(
            defaultElevation = defaultElevation
        )
    ) {
        Column {
            if (!camera.snapshotUrl.isNullOrBlank()) {
                Box(
                    modifier = Modifier
                        .height(207.dp)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                ) {
                    val painter = rememberAsyncImagePainter(
                        ImageRequest.Builder(LocalContext.current).data(camera.snapshotUrl)
                            .crossfade(true).build()
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

                    val isRecordingVisibility = if (camera.isRecording) 1f else 0f
                    Image(
                        modifier = Modifier
                            .alpha(isRecordingVisibility)
                            .padding(8.dp)
                            .align(Alignment.TopStart),
                        painter = painterResource(id = R.drawable.ic_recording),
                        contentScale = ContentScale.None,
                        contentDescription = null
                    )

                    val isFavoriteVisibility = if (camera.isFavorite) 1f else 0f
                    Image(
                        modifier = Modifier
                            .alpha(isFavoriteVisibility)
                            .padding(3.dp)
                            .align(Alignment.TopEnd),
                        painter = painterResource(id = R.drawable.ic_is_favorite),
                        contentScale = ContentScale.None,
                        contentDescription = null
                    )
                }
            }

            Text(
                text = camera.name,
                modifier = Modifier.padding(start = 16.dp, top = 22.dp, bottom = 30.dp),
                textAlign = TextAlign.Start
            )
        }
    }
}

// @Preview(
//    widthDp = 375, heightDp = 557, showBackground = true, backgroundColor = 0xFF00FF00,
//    name = "Ret", uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL,
// )
// @Composable
// private fun PrevCameraTile() {
//    AppTheme {
//        CameraTile(
//            modifier = Modifier
//                .width(333.dp)
//                .wrapContentHeight()
//                .padding(horizontal = large),
//            cameraItem = cameraItemSnapshot,
//            isTest = true
//        )
//    }
// }

@Preview(
    widthDp = 375,
    heightDp = 557,
    showBackground = true,
    backgroundColor = 0xFF00FF00,
    name = "Ret2",
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
private fun PrevRoomTitle() {
    AppTheme {
        RoomTitle(titleText = "Гостиная")
    }
}

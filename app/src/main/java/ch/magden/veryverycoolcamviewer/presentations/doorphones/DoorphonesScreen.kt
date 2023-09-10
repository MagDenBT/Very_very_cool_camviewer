package ch.magden.veryverycoolcamviewer.presentations.doorphones

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
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.LocalTextStyle
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ch.magden.veryverycoolcamviewer.R
import ch.magden.veryverycoolcamviewer.model.entities.Doorphone
import ch.magden.veryverycoolcamviewer.presentations.sharedelements.ACTION_ITEM_SIDE_PADDING
import ch.magden.veryverycoolcamviewer.presentations.sharedelements.ACTION_ITEM_WIDTH
import ch.magden.veryverycoolcamviewer.presentations.sharedelements.ANIMATION_DURATION
import ch.magden.veryverycoolcamviewer.presentations.sharedelements.ActionsRow
import ch.magden.veryverycoolcamviewer.presentations.sharedelements.EditDialog
import ch.magden.veryverycoolcamviewer.presentations.sharedelements.LoadingScreen
import ch.magden.veryverycoolcamviewer.presentations.sharedelements.MIN_DRAG_AMOUNT
import ch.magden.veryverycoolcamviewer.ui.theme.gray_dense
import ch.magden.veryverycoolcamviewer.utils.Resource
import ch.magden.veryverycoolcamviewer.utils.regardingDp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import kotlin.math.roundToInt

val DOORPHONE_CARD_OFFSET = (ACTION_ITEM_WIDTH.value + ACTION_ITEM_SIDE_PADDING.value) * 2

@Composable
fun PreloadDoorphonesScreen(viewModel: DoorphonesViewModel) {
    val doorphoneItems by viewModel.doorphones.collectAsStateWithLifecycle()

    when (doorphoneItems) {
        is Resource.Success -> DoorphonesScreen(
            doorphoneItems = doorphoneItems.data!!,
            onFavorite = { doorphone -> viewModel.switchDoorphoneIsFavorite(doorphone) },
            onNameEdit = { newName, doorphone -> viewModel.setDoorphoneName(newName, doorphone) }
        )

        is Resource.Loading -> LoadingScreen()
        is Resource.Error -> LoadingScreen() // по-хорошему нужная отдельная логика отработки ошибок
    }
}

@Composable
private fun DoorphonesScreen(
    doorphoneItems: List<Doorphone>,
    onFavorite: (Doorphone) -> Unit,
    onNameEdit: (String, Doorphone) -> Unit
) {
    LazyColumn(
        modifier = Modifier.padding(
            top = 5.dp,
            start = 21.dp,
            end = 21.dp
        ),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(
            items = doorphoneItems,
            key = { doorphoneItem -> doorphoneItem.id }
        ) { doorphoneItem ->

            var showEditDialog by rememberSaveable { mutableStateOf(false) }
            var showActions by rememberSaveable {
                mutableStateOf(false)
            }

            if (showEditDialog) {
                EditDialog(value = doorphoneItem.name, setShowDialog = {
                    showEditDialog = it
                }) {
                    onNameEdit(it, doorphoneItem)
                    showActions = false
                }
            }

            Spacer(Modifier.height(11.dp))

            DoorphoneTile(
                modifier = Modifier.padding(bottom = 3.dp)
                    .width(333.dp)
                    .wrapContentHeight(),
                doorphone = doorphoneItem,
                onFavorite = { onFavorite(it) },
                setShowEditDialog = {
                    showEditDialog = it
                },
                isShowingActions = showActions,
                setShowActions = { showActions = it }
            )
        }
    }
}

@Composable
private fun DoorphoneTile(
    doorphone: Doorphone,
    isShowingActions: Boolean,
    onFavorite: (Doorphone) -> Unit,
    setShowActions: (Boolean) -> Unit,
    setShowEditDialog: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.CenterEnd
    ) {
        ActionsRow(
            actionIconWidth = ACTION_ITEM_WIDTH,
            actionIconSidePadding = ACTION_ITEM_SIDE_PADDING,
            setShowEditDialog = {
                setShowEditDialog(it)
            },
            onFavorite = {
                onFavorite(doorphone)
                setShowActions(false)
            },
            isFavorite = doorphone.isFavorite
        )
        DoorphoneDraggebleCard(
            doorphone = doorphone,
            isShowingActions = isShowingActions,
            setShowActions = setShowActions
        )
    }
}

@Suppress("UnusedTransitionTargetStateParameter")
@Composable
private fun DoorphoneDraggebleCard(
    doorphone: Doorphone,
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
        targetValueByState = { if (isShowingActions) -DOORPHONE_CARD_OFFSET.regardingDp() else 0f }
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
                detectTapGestures(
                    onLongPress = { setShowActions(!isShowingActions) },
                    onDoubleTap = { setShowActions(!isShowingActions) }
                )
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
        val hasSnapshot = !doorphone.snapshotUrl.isNullOrBlank()

        Column {
            if (hasSnapshot) {
                Box(
                    modifier = Modifier
                        .height(207.dp)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                ) {
                    val painter = rememberAsyncImagePainter(
                        ImageRequest.Builder(LocalContext.current)
                            .data(doorphone.snapshotUrl).crossfade(true).build()
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

                    val isFavoriteVisibility = if (doorphone.isFavorite) 1f else 0f
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
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 24.72.dp, start = 16.48.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val columnPadding = if (hasSnapshot) {
                Modifier.padding(
                    top = 14.dp,
                    bottom = 24.dp
                )
            } else {
                Modifier.padding(top = 22.dp, bottom = 30.dp)
            }
            Column(modifier = columnPadding) {
                Text(text = doorphone.name)

                if (doorphone.online) {
                    Text(
                        text = stringResource(id = R.string.online_text),
                        style = LocalTextStyle.current.copy(
                            fontWeight = FontWeight.W300,
                            fontSize = 14.sp,
                            lineHeight = 20.64.sp,
                            color = gray_dense
                        )
                    )
                }
            }

            Image(
                modifier = Modifier.align(Alignment.CenterVertically),
                painter = painterResource(id = R.drawable.ic_lock),
                contentDescription = null,
                contentScale = ContentScale.None
            )
        }
    }
}

// @Preview(
//    widthDp = 375, heightDp = 557, showBackground = true, backgroundColor = 0xFF00FF00,
//    name = "Ret", uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL,
// )
// @Composable
// private fun PrevDoorphoneTile() {
//    AppTheme {
//        DoorphoneTile(
//
//            doorphoneItem = doorphoneItemSnapshot,
//            modifier = Modifier
//                .width(333.dp)
//                .wrapContentHeight()
//                .padding(horizontal = large),
//        )
//    }
// }

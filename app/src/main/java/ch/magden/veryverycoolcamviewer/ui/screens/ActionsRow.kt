package ch.magden.veryverycoolcamviewer.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ch.magden.veryverycoolcamviewer.R

const val ANIMATION_DURATION = 500
const val MIN_DRAG_AMOUNT = 20
val ACTION_ITEM_WIDTH = 36.dp
val ACTION_ITEM_SIDE_PADDING = 9.dp

@Composable
fun ActionsRow(
    actionIconWidth: Dp,
    actionIconSidePadding: Dp,
    setShowEditDialog: ((Boolean) -> Unit)? = null,
    onFavorite: () -> Unit,
    isFavorite: Boolean
) {
    Row(
        modifier = Modifier.wrapContentWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val shouldDisplayEditButton = setShowEditDialog != null

        if (shouldDisplayEditButton) {
            IconButton(
                modifier = Modifier
                    .padding(start = actionIconSidePadding, end = 2.dp)
                    .width(actionIconWidth),
                onClick = { setShowEditDialog!!(true) },
                content = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_edit),
                        tint = Color.Unspecified,
                        contentDescription = null
                    )
                }
            )
        }

        val rightPadding = if (shouldDisplayEditButton) 0.dp else 2.dp
        IconButton(
            modifier = Modifier
                .padding(start = actionIconSidePadding, end = rightPadding)
                .width(actionIconWidth),
            onClick = onFavorite,
            content = {
                val iconId =
                    if (isFavorite) R.drawable.ic_is_favorite else R.drawable.ic_not_favorite
                Icon(
                    painter = painterResource(id = iconId),
                    tint = Color.Unspecified,
                    contentDescription = null
                )
            }
        )
    }
}

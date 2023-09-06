package ch.magden.veryverycoolcamviewer.presentations.sharedelements

import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import ch.magden.veryverycoolcamviewer.R

@Composable
fun ActionsRow(
    actionIconWidth: Dp,
    actionIconSidePadding: Dp,
    onEdit: ((String) -> Unit)? = null,
    onFavorite: () -> Unit,
    isFavorite: Boolean,
) {
    Row(modifier = Modifier.wrapContentWidth(),horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically) {
        val modifier = Modifier
            .padding(start = actionIconSidePadding)
            .width(actionIconWidth)
        if(onEdit != null) {
            IconButton(
                modifier = modifier,
                onClick = { onEdit("Новое имя") },
                content = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_edit),
                        tint =  Color.Unspecified,
                        contentDescription = null,
                    )
                },
            )
        }
        IconButton(
            modifier = modifier,
            onClick = onFavorite,
            content = {
                val iconId = if (isFavorite) R.drawable.ic_is_favorite else R.drawable.ic_not_favorite
                Icon(
                    painter = painterResource(id = iconId),
                    tint =  Color.Unspecified,
                    contentDescription = null,
                )
            }
        )
    }
}
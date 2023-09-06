package ch.magden.veryverycoolcamviewer.model.entities

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class Doorphone(
    val id: Int,
    val name: String,
    val room: String?,
    val snapshotUrl: String?,
    val isFavorite: Boolean,
    val online: Boolean = !snapshotUrl.isNullOrBlank()
)
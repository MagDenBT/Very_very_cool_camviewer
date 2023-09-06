package ch.magden.veryverycoolcamviewer.model.entities

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class Doorphone(
    val id: Int,
    val name: MutableState<String>,
    val room: MutableState<String?>,
    val snapshotUrl: MutableState<String?>,
    val isFavorite: MutableState<Boolean>,
    val online: MutableState<Boolean> = mutableStateOf(!snapshotUrl.value.isNullOrBlank())
)
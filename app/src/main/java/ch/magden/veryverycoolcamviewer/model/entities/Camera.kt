package ch.magden.veryverycoolcamviewer.model.entities

import androidx.compose.runtime.MutableState

data class Camera(
    val id: Int,
    val name: MutableState<String>,
    val room: String? = null,
    val snapshotUrl: String? = null,
    val isFavorite: MutableState<Boolean>,
    val isRecording: MutableState<Boolean>
)
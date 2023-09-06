package ch.magden.veryverycoolcamviewer.model.entities

import androidx.compose.runtime.MutableState

data class Camera(
    val id: Int,
    val name: String,
    val room: String? = null,
    val snapshotUrl: String? = null,
    val isFavorite: Boolean,
    val isRecording: Boolean
)
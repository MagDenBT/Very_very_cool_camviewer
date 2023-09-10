package ch.magden.veryverycoolcamviewer.core.domainmodels

data class Camera(
    val id: Int,
    val name: String,
    val room: String? = null,
    val snapshotUrl: String? = null,
    val isFavorite: Boolean,
    val isRecording: Boolean
)

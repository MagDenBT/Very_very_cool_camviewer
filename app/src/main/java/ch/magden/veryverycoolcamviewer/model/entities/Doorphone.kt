package ch.magden.veryverycoolcamviewer.model.entities

data class Doorphone(
    val id: Int,
    val name: String,
    val room: String?,
    val snapshotUrl: String?,
    val isFavorite: Boolean,
    val online: Boolean = !snapshotUrl.isNullOrBlank()
)

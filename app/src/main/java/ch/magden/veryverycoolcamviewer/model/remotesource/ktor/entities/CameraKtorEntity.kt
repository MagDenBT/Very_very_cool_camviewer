package ch.magden.veryverycoolcamviewer.model.remotesource.ktor.entities

import ch.magden.veryverycoolcamviewer.model.entities.Camera
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class GetCamerasKtorEntity(
    val success: Boolean,
    val data: RoomsAndCamerasKtorEntity,
)


@Serializable
data class RoomsAndCamerasKtorEntity(
    val room: List<String>,
    val cameras: List<CameraKtorEntity>,
)

@Serializable
data class CameraKtorEntity(
    val id: Int,
    val name: String,
    val room: String? = null,
    @SerialName("snapshot")
    val snapshotUrl: String? = null,
    @SerialName("favorites")
    val isFavorite: Boolean,
    @SerialName("rec")
    val isRecording: Boolean,
) {
    fun toCamera() = Camera(
        id = id,
        name = name,
        room = room,
        snapshotUrl = snapshotUrl,
        isFavorite = isFavorite,
        isRecording = isRecording
    )
}
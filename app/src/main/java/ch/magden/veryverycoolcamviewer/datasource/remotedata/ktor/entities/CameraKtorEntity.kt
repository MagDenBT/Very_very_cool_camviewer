package ch.magden.veryverycoolcamviewer.datasource.remotedata.ktor.entities

import ch.magden.veryverycoolcamviewer.core.domainmodels.Camera
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetCamerasKtorEntity(
    @SerialName("success")
    val success: Boolean,
    @SerialName("data")
    val data: RoomsAndCamerasKtorEntity
)

@Serializable
data class RoomsAndCamerasKtorEntity(
    @SerialName("room")
    val room: List<String>,
    @SerialName("cameras")
    val cameras: List<CameraKtorEntity>
)

@Serializable
data class CameraKtorEntity(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("room")
    val room: String? = null,
    @SerialName("snapshot")
    val snapshotUrl: String? = null,
    @SerialName("favorites")
    val isFavorite: Boolean,
    @SerialName("rec")
    val isRecording: Boolean
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

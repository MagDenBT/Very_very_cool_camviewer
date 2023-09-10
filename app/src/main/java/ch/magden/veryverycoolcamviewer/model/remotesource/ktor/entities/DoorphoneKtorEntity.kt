package ch.magden.veryverycoolcamviewer.model.remotesource.ktor.entities

import ch.magden.veryverycoolcamviewer.model.entities.Doorphone
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetDoorphonesKtorEntity(
    val success: Boolean,
    val data: List<DoorphoneKtorEntity>
)

@Serializable
data class DoorphoneKtorEntity(
    val id: Int,
    val name: String,
    val room: String? = null,
    @SerialName("snapshot") val snapshotUrl: String? = null,
    @SerialName("favorites") val isFavorite: Boolean
) {

    fun toDoorphone() = Doorphone(
        id = id,
        name = name,
        room = room,
        snapshotUrl = snapshotUrl,
        isFavorite = isFavorite
    )
}

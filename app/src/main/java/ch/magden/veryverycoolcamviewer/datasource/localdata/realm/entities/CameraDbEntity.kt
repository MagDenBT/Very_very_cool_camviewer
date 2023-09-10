package ch.magden.veryverycoolcamviewer.datasource.localdata.realm.entities

import ch.magden.veryverycoolcamviewer.core.domainmodels.Camera
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class CameraDbEntity : RealmObject() {
    @PrimaryKey
    var id: Int = 0
    var name: String = ""
    var room: String? = null
    var snapshotUrl: String? = null
    var isFavorite: Boolean = false
    var isRecording: Boolean = false

    fun toCamera() = Camera(
        id = id,
        name = name,
        room = room,
        snapshotUrl = snapshotUrl,
        isFavorite = isFavorite,
        isRecording = isRecording
    )

    fun refillFromCamera(camera: Camera) {
        name = camera.name
        room = camera.room
        snapshotUrl = camera.snapshotUrl
        isFavorite = camera.isFavorite
        isRecording = camera.isRecording
    }
}

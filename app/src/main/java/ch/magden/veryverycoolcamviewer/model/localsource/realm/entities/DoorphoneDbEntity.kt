package ch.magden.veryverycoolcamviewer.model.localsource.realm.entities

import ch.magden.veryverycoolcamviewer.model.entities.Doorphone
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class DoorphoneDbEntity : RealmObject() {
    @PrimaryKey
    var id: Int = 0
    var name: String = ""
    var room: String? = null
    var snapshotUrl: String? = null
    var isFavorite: Boolean = false

    fun toDoorphone() = Doorphone(
        id = id,
        name = name,
        room = room,
        snapshotUrl = snapshotUrl,
        isFavorite = isFavorite
    )

    fun refillFromDoorphone(doorphone: Doorphone) {
        name = doorphone.name
        room = doorphone.room
        snapshotUrl = doorphone.snapshotUrl
        isFavorite = doorphone.isFavorite
    }
}

package ch.magden.veryverycoolcamviewer.model.localsource.realm

import ch.magden.veryverycoolcamviewer.model.entities.Camera
import ch.magden.veryverycoolcamviewer.model.entities.Doorphone
import ch.magden.veryverycoolcamviewer.model.localsource.DataLocalSource
import ch.magden.veryverycoolcamviewer.model.localsource.realm.entities.CameraDbEntity
import ch.magden.veryverycoolcamviewer.model.localsource.realm.entities.DoorphoneDbEntity
import io.realm.Realm
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class RealmDb : DataLocalSource {

    private val db = Realm.getDefaultInstance()

    private val realmCameras = db.where(CameraDbEntity::class.java).findAll()


    private val _cameras by lazy { flowOf( realmCameras.map { it.toCamera() }.toList() )}

    private val realmDoorphones = db.where(DoorphoneDbEntity::class.java).findAll()

    private val _doorphones by lazy { flowOf( realmDoorphones.map { it.toDoorphone() }.toList() )}



    override fun getCameras(): Flow<List<Camera>> = _cameras

    override suspend fun insertOrUpdateCameras(cameras: List<Camera>) {
        db.executeTransactionAsync { realm ->
            cameras.forEach {camera ->
               val entity = realm.createObject(CameraDbEntity::class.java).apply {
                id = camera.id
                name = camera.name
                room = camera.room
                snapshotUrl = camera.snapshotUrl
                isFavorite = camera.isFavorite
                isRecording = camera.isRecording
               }
                realm.insertOrUpdate(entity)
            }
        }
    }

    override suspend fun deleteAllCameras() {
        db.delete(CameraDbEntity::class.java)
    }

    override fun getDoorphones() = _doorphones

    override suspend fun insertOrUpdateDoorphones(doorphones: List<Doorphone>) {
        db.executeTransactionAsync { realm ->
            doorphones.forEach {doorphone ->
                val entity = realm.createObject(DoorphoneDbEntity::class.java).apply {
                    id = doorphone.id
                    name = doorphone.name
                    room = doorphone.room
                    snapshotUrl = doorphone.snapshotUrl
                    isFavorite = doorphone.isFavorite
                }
                realm.insertOrUpdate(entity)
            }
        }
    }

    override suspend fun deleteAllDoorphones() {
        db.delete(DoorphoneDbEntity::class.java)
    }

    protected fun finalize() {
        db.close()
    }
}
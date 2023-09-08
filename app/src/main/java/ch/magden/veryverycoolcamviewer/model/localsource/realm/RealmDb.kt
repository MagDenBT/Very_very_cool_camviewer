package ch.magden.veryverycoolcamviewer.model.localsource.realm

import ch.magden.veryverycoolcamviewer.model.entities.Camera
import ch.magden.veryverycoolcamviewer.model.entities.Doorphone
import ch.magden.veryverycoolcamviewer.model.localsource.DataLocalSource
import ch.magden.veryverycoolcamviewer.model.localsource.realm.entities.CameraDbEntity
import ch.magden.veryverycoolcamviewer.model.localsource.realm.entities.DoorphoneDbEntity
import io.realm.Realm
import io.realm.kotlin.executeTransactionAwait
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class RealmDb : DataLocalSource {

    override fun getCameras(): Flow<List<Camera>> {
        val db = Realm.getDefaultInstance()
        val realmCameras = db.copyFromRealm(db.where(CameraDbEntity::class.java).findAll()).also { db.close() }

       return  flowOf( realmCameras.map { it.toCamera() }.toList() )
    }

    override suspend fun insertOrUpdateCameras(cameras: List<Camera>) {
        val db = Realm.getDefaultInstance()
        db.executeTransactionAsync { realm ->
            cameras.forEach {camera ->
               val entity = realm.createObject(CameraDbEntity::class.java,camera.id).apply {
                name = camera.name
                room = camera.room
                snapshotUrl = camera.snapshotUrl
                isFavorite = camera.isFavorite
                isRecording = camera.isRecording
               }
                realm.insertOrUpdate(entity)
            }
        }

        db.close()
    }

    override suspend fun deleteAllCameras() {
     Realm.getDefaultInstance().run {
            beginTransaction()
            delete(CameraDbEntity::class.java)
            commitTransaction()
            close()
        }
    }

    override fun getDoorphones() : Flow<List<Doorphone>> {
        val db = Realm.getDefaultInstance()
        val realmDoorphones =
            db.copyFromRealm(db.where(DoorphoneDbEntity::class.java).findAll()).also { db.close() }

        return flowOf(realmDoorphones.map { it.toDoorphone() }.toList())
    }
    override suspend fun insertOrUpdateDoorphones(doorphones: List<Doorphone>) {
        val db = Realm.getDefaultInstance()
        db.executeTransactionAsync { realm ->
            doorphones.forEach {doorphone ->
                val entity = realm.createObject(DoorphoneDbEntity::class.java , doorphone.id).apply {
                    name = doorphone.name
                    room = doorphone.room
                    snapshotUrl = doorphone.snapshotUrl
                    isFavorite = doorphone.isFavorite
                }
                realm.insertOrUpdate(entity)
            }
        }
        db.close()
    }

    override suspend fun deleteAllDoorphones() {
        Realm.getDefaultInstance().run {
            beginTransaction()
            delete(DoorphoneDbEntity::class.java)
            commitTransaction()
            close()
        }
    }

}
package ch.magden.veryverycoolcamviewer.datasource.localdata.realm

import ch.magden.veryverycoolcamviewer.core.domainmodels.Camera
import ch.magden.veryverycoolcamviewer.core.domainmodels.Doorphone
import ch.magden.veryverycoolcamviewer.datasource.localdata.LocalData
import ch.magden.veryverycoolcamviewer.datasource.localdata.realm.entities.CameraDbEntity
import ch.magden.veryverycoolcamviewer.datasource.localdata.realm.entities.DoorphoneDbEntity
import io.realm.Realm
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Singleton
class RealmDb @Inject constructor() : LocalData {

    override fun getCameras(): Flow<List<Camera>> {
        val db = Realm.getDefaultInstance()
        val realmCameras =
            db.copyFromRealm(db.where(CameraDbEntity::class.java).findAll()).also { db.close() }
        return flowOf(realmCameras.map { it.toCamera() }.toList())
    }

    override suspend fun insertOrUpdateCameras(cameras: List<Camera>) {
        val db = Realm.getDefaultInstance()
        db.executeTransaction { realm ->
            cameras.forEach { camera ->
                var entity =
                    realm.where(CameraDbEntity::class.java).equalTo("id", camera.id).findFirst()
                if (entity == null) {
                    entity = realm.createObject(CameraDbEntity::class.java, camera.id)
                }

                entity?.refillFromCamera(camera)
                entity?.let { realm.insertOrUpdate(it) }
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

    override fun getDoorphones(): Flow<List<Doorphone>> {
        val db = Realm.getDefaultInstance()
        val realmDoorphones =
            db.copyFromRealm(db.where(DoorphoneDbEntity::class.java).findAll()).also { db.close() }

        return flowOf(realmDoorphones.map { it.toDoorphone() }.toList())
    }

    override suspend fun insertOrUpdateDoorphones(doorphones: List<Doorphone>) {
        val db = Realm.getDefaultInstance()
        db.executeTransaction { realm ->
            doorphones.forEach { doorphone ->
                var entity =
                    realm.where(DoorphoneDbEntity::class.java).equalTo("id", doorphone.id).findFirst()
                if (entity == null) {
                    entity = realm.createObject(DoorphoneDbEntity::class.java, doorphone.id)
                }
                entity?.refillFromDoorphone(doorphone)
                entity?.let { realm.insertOrUpdate(it) }
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

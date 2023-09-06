package ch.magden.veryverycoolcamviewer.model.localsource

import ch.magden.veryverycoolcamviewer.model.entities.Camera
import ch.magden.veryverycoolcamviewer.model.entities.Doorphone
import kotlinx.coroutines.flow.Flow

interface DataLocalSource {
    fun getCameras(): Flow<List<Camera>>

    suspend fun insertOrUpdateCameras(cameras: List<Camera>)

    suspend fun deleteAllCameras()

    fun getDoorphones(): Flow<List<Doorphone>>

    suspend fun insertOrUpdateDoorphones(doorphones: List<Doorphone>)

    suspend fun deleteAllDoorphones()

}
package ch.magden.veryverycoolcamviewer.datasource.localdata

import ch.magden.veryverycoolcamviewer.core.domainmodels.Camera
import ch.magden.veryverycoolcamviewer.core.domainmodels.Doorphone
import kotlinx.coroutines.flow.Flow

interface LocalData {

    fun getCameras(): Flow<List<Camera>>

    suspend fun insertOrUpdateCameras(cameras: List<Camera>)

    suspend fun deleteAllCameras()

    fun getDoorphones(): Flow<List<Doorphone>>

    suspend fun insertOrUpdateDoorphones(doorphones: List<Doorphone>)

    suspend fun deleteAllDoorphones()
}

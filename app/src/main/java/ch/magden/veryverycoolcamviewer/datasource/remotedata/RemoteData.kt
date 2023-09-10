package ch.magden.veryverycoolcamviewer.datasource.remotedata

import ch.magden.veryverycoolcamviewer.core.domainmodels.Camera
import ch.magden.veryverycoolcamviewer.core.domainmodels.Doorphone
import kotlinx.coroutines.flow.Flow

interface RemoteData {

    suspend fun fetchCameras(): Flow<List<Camera>>

    suspend fun saveCameras(cameras: List<Camera>)

    suspend fun fetchDoorphones(): Flow<List<Doorphone>>

    suspend fun saveDoorphones(cameras: List<Doorphone>)
}

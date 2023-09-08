package ch.magden.veryverycoolcamviewer.model.remotesource

import ch.magden.veryverycoolcamviewer.model.entities.Camera
import ch.magden.veryverycoolcamviewer.model.entities.Doorphone
import kotlinx.coroutines.flow.Flow

interface DataRemoteSource {

    suspend fun fetchCameras(): Flow<List<Camera>>

    suspend fun saveCameras(cameras: List<Camera>)

    suspend fun fetchDoorphones(): Flow<List<Doorphone>>

    suspend fun saveDoorphones(cameras: List<Doorphone>)

}
package ch.magden.veryverycoolcamviewer.model.remotesource

import ch.magden.veryverycoolcamviewer.model.entities.Camera
import ch.magden.veryverycoolcamviewer.model.entities.Doorphone
import kotlinx.coroutines.flow.Flow

interface DataRemoteSource {

    fun fetchCameras(): Flow<List<Camera>>

    suspend fun saveCameras(cameras: List<Camera>)

    fun fetchDoorphones(): Flow<List<Doorphone>>

    suspend fun saveDoorphones(cameras: List<Doorphone>)

}
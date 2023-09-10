package ch.magden.veryverycoolcamviewer.datasource.remotedata.ktor

import ch.magden.veryverycoolcamviewer.core.domainmodels.Camera
import ch.magden.veryverycoolcamviewer.core.domainmodels.Doorphone
import ch.magden.veryverycoolcamviewer.datasource.remotedata.RemoteData
import ch.magden.veryverycoolcamviewer.datasource.remotedata.ktor.entities.GetCamerasKtorEntity
import ch.magden.veryverycoolcamviewer.datasource.remotedata.ktor.entities.GetDoorphonesKtorEntity
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Singleton
class KtorHttpClient @Inject constructor(private val httpClient: HttpClient) : RemoteData {

    override suspend fun fetchCameras(): Flow<List<Camera>> {
        val response = httpClient.get<GetCamerasKtorEntity>(DataEndpoints.GET_CAMERAS_AND_ROOMS)
        val camerasList = response.data.cameras.map { it.toCamera() }
        return flow { emit(camerasList) }
    }

    override suspend fun saveCameras(cameras: List<Camera>) {
        TODO("Not yet implemented")
    }

    override suspend fun fetchDoorphones(): Flow<List<Doorphone>> {
        val response = httpClient.get<GetDoorphonesKtorEntity>(DataEndpoints.GET_DOORPHONES)
        val doorphoneList = response.data.map { it.toDoorphone() }
        return flow { emit(doorphoneList) }
    }

    override suspend fun saveDoorphones(cameras: List<Doorphone>) {
        TODO("Not yet implemented")
    }
}

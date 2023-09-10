package ch.magden.veryverycoolcamviewer.model.remotesource.ktor

import ch.magden.veryverycoolcamviewer.model.entities.Camera
import ch.magden.veryverycoolcamviewer.model.entities.Doorphone
import ch.magden.veryverycoolcamviewer.model.remotesource.DataRemoteSource
import ch.magden.veryverycoolcamviewer.model.remotesource.ktor.entities.GetCamerasKtorEntity
import ch.magden.veryverycoolcamviewer.model.remotesource.ktor.entities.GetDoorphonesKtorEntity
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json

class KtorHtttpClient : DataRemoteSource {

    private val TIME_OUT = 30_000

    private val ktorHttpClient: HttpClient = HttpClient(Android) {
        install(JsonFeature) {
            serializer = KotlinxSerializer(
                Json {
                    prettyPrint = true
                    ignoreUnknownKeys = true
                    isLenient = true
                    encodeDefaults = false
                }
            )
        }
        engine {
            connectTimeout = TIME_OUT
            socketTimeout = TIME_OUT
        }
    }

//        install(ResponseObserver) {
//            onResponse { response ->
//                Log.d("HTTP status:", "${response.status.value}")
//            }
//        }

//        defaultRequest {
//            url(DataEndpoints.BASE_URL)
//           header(HttpHeaders.ContentType, ContentType.Application.Json)
//        }

//    }

    override suspend fun fetchCameras(): Flow<List<Camera>> {
        val response = ktorHttpClient.get<GetCamerasKtorEntity>(DataEndpoints.GET_CAMERAS_AND_ROOMS)
        val camerasList = response.data.cameras.map { it.toCamera() }
        return flow { emit(camerasList) }
    }

    override suspend fun saveCameras(cameras: List<Camera>) {
        TODO("Not yet implemented")
    }

    override suspend fun fetchDoorphones(): Flow<List<Doorphone>> {
        val response = ktorHttpClient.get<GetDoorphonesKtorEntity>(DataEndpoints.GET_DOORPHONES)
        val doorphoneList = response.data.map { it.toDoorphone() }
        return flow { emit(doorphoneList) }
    }

    override suspend fun saveDoorphones(cameras: List<Doorphone>) {
        TODO("Not yet implemented")
    }
}

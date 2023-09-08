package ch.magden.veryverycoolcamviewer.model.remotesource.ktor

import android.util.Log
import ch.magden.veryverycoolcamviewer.model.entities.Camera
import ch.magden.veryverycoolcamviewer.model.entities.Doorphone
import ch.magden.veryverycoolcamviewer.model.remotesource.DataRemoteSource
import ch.magden.veryverycoolcamviewer.model.remotesource.ktor.entities.GetCamerasKtorEntity
import ch.magden.veryverycoolcamviewer.model.remotesource.ktor.entities.GetDoorphonesKtorEntity
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android


import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json


class KtorHtttpClient : DataRemoteSource {

    private val TIME_OUT = 60_000

    private val ktorHttpClient = HttpClient(Android) {

        expectSuccess = true
        install(ContentNegotiation) {
            Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true

            }

        }

        engine {
            connectTimeout = TIME_OUT
            socketTimeout = TIME_OUT
        }



        install(ResponseObserver) {
            onResponse { response ->
                Log.d("HTTP status:", "${response.status.value}")
            }
        }

        defaultRequest {
            url(DataEndpoints.BASE_URL)
            header(HttpHeaders.ContentType, ContentType.Application.Json)
        }

    }


    override suspend fun fetchCameras(): Flow<List<Camera>> {
        val response = ktorHttpClient.get(DataEndpoints.GET_CAMERAS_AND_ROOMS)
        val dataEntity = response.body<GetCamerasKtorEntity>()
        val camerasList = dataEntity.data.cameras.map { it.toCamera() }
        return flow { emit(camerasList) }
    }

    override suspend fun saveCameras(cameras: List<Camera>) {
        TODO("Not yet implemented")
    }

    override suspend fun fetchDoorphones(): Flow<List<Doorphone>> {
        val response = ktorHttpClient.get(DataEndpoints.GET_DOORPHONES)
        val dataEntity = response.body<GetDoorphonesKtorEntity>()
        val doorphonesList = dataEntity.data.map { it.toDoorphone() }
        return flow { emit(doorphonesList) }
    }

    override suspend fun saveDoorphones(cameras: List<Doorphone>) {
        TODO("Not yet implemented")
    }
}
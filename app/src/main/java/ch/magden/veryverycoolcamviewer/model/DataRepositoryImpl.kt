package ch.magden.veryverycoolcamviewer.model

import androidx.compose.runtime.mutableStateOf
import ch.magden.veryverycoolcamviewer.model.entities.Camera
import ch.magden.veryverycoolcamviewer.model.entities.Doorphone
import ch.magden.veryverycoolcamviewer.model.localsource.DataLocalSource
import ch.magden.veryverycoolcamviewer.model.remotesource.DataRemoteSource
import ch.magden.veryverycoolcamviewer.utils.Resource
import ch.magden.veryverycoolcamviewer.utils.networkBoundResource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DataRepositoryImpl(
    private val localSource: DataLocalSource,
    private val remoteSource: DataRemoteSource,
    private val ioDispatcher: CoroutineDispatcher,
) : DataRepository {

    private var cameras: MutableStateFlow<Resource<List<Camera>>> = MutableStateFlow(
        Resource.Loading(
            emptyList()
        )
    )
    private var doorphones: MutableStateFlow<Resource<List<Doorphone>>> = MutableStateFlow(
        Resource.Loading(
            emptyList()
        )
    )

    init {
        CoroutineScope(ioDispatcher).launch {

            networkBoundResource(query = {
                localSource.getCameras()
            }, fetch = {
                remoteSource.fetchCameras()
            }, shouldFetch = { queryData -> queryData.isEmpty() }, saveFetchResult = { camera ->
                localSource.deleteAllCameras()
                camera.collectLatest { fetchedResult ->
                    localSource.insertOrUpdateCameras(
                        fetchedResult
                    )
                }

            }).collect { cameras.value = it }

            networkBoundResource(query = {
                localSource.getDoorphones()
            }, fetch = {
                remoteSource.fetchDoorphones()
            }, shouldFetch = { queryData -> queryData.isEmpty() }, saveFetchResult = { camera ->
                localSource.deleteAllDoorphones()
                camera.collectLatest { fetchedResult ->
                    localSource.insertOrUpdateDoorphones(
                        fetchedResult
                    )
                }
            }).collect { doorphones.value = it }
        }
    }

    override fun getCameras() = cameras

    override fun setCamera(camera: Camera) {
        CoroutineScope(ioDispatcher).launch {
            localSource.insertOrUpdateCameras(listOf(camera))
            localSource.getCameras().collect { cameras.value = Resource.Success(it) }
        }
    }

    override fun getDoorphones() = doorphones

    override fun setDoorphones(doorphone: Doorphone) {
        CoroutineScope(ioDispatcher).launch {
            localSource.insertOrUpdateDoorphones(listOf(doorphone))
            localSource.getDoorphones().collect { doorphones.value = Resource.Success(it) }
        }
    }
}
package ch.magden.veryverycoolcamviewer.datasource

import ch.magden.veryverycoolcamviewer.core.domainmodels.Camera
import ch.magden.veryverycoolcamviewer.core.domainmodels.Doorphone
import ch.magden.veryverycoolcamviewer.core.utils.Resource
import ch.magden.veryverycoolcamviewer.core.utils.networkBoundResource
import ch.magden.veryverycoolcamviewer.datasource.localdata.LocalData
import ch.magden.veryverycoolcamviewer.datasource.remotedata.RemoteData
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Singleton
class DataRepositoryImpl @Inject constructor(
    private val localData: LocalData,
    private val remoteData: RemoteData,
    private val ioDispatcher: CoroutineDispatcher
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
                localData.getCameras()
            }, fetch = {
                remoteData.fetchCameras()
            }, shouldFetch = { queryData -> queryData.isEmpty() }, saveFetchResult = { camera ->
                localData.deleteAllCameras()
                camera.collectLatest { fetchedResult ->
                    localData.insertOrUpdateCameras(
                        fetchedResult
                    )
                }
            }).collect { cameras.value = it }

            networkBoundResource(query = {
                localData.getDoorphones()
            }, fetch = {
                remoteData.fetchDoorphones()
            }, shouldFetch = { queryData -> queryData.isEmpty() }, saveFetchResult = { camera ->
                localData.deleteAllDoorphones()
                camera.collectLatest { fetchedResult ->
                    localData.insertOrUpdateDoorphones(
                        fetchedResult
                    )
                }
            }).collect { doorphones.value = it }
        }
    }

    override fun getCameras() = cameras

    override fun setCamera(camera: Camera) {
        CoroutineScope(ioDispatcher).launch {
            localData.insertOrUpdateCameras(listOf(camera))
            localData.getCameras().collect { cameras.value = Resource.Success(it) }
        }
    }

    override fun getDoorphones() = doorphones

    override fun setDoorphones(doorphone: Doorphone) {
        CoroutineScope(ioDispatcher).launch {
            localData.insertOrUpdateDoorphones(listOf(doorphone))
            localData.getDoorphones().collect { doorphones.value = Resource.Success(it) }
        }
    }
}

package ch.magden.veryverycoolcamviewer.model

import ch.magden.veryverycoolcamviewer.model.entities.Camera
import ch.magden.veryverycoolcamviewer.model.entities.Doorphone
import ch.magden.veryverycoolcamviewer.model.localsource.DataLocalSource
import ch.magden.veryverycoolcamviewer.model.remotesource.DataRemoteSource
import ch.magden.veryverycoolcamviewer.utils.Resource
import ch.magden.veryverycoolcamviewer.utils.networkBoundResource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DataRepositoryImpl(
    private val localSource: DataLocalSource, private val remoteSource: DataRemoteSource, private val ioDispatcher: CoroutineDispatcher
) : DataRepository {


    override fun getCameras(): Flow<Resource<List<Camera>>> = networkBoundResource(

        query = {
            localSource.getCameras()
        },
        fetch = {
            remoteSource.fetchCameras()
        },
        shouldFetch = {queryData-> queryData.isEmpty()},
        saveFetchResult = { camera ->
            localSource.deleteAllCameras()
            camera.collectLatest { fetchedResult -> localSource.insertOrUpdateCameras(fetchedResult) }

        }

    )

    override fun getRooms(): Flow<Resource<List<String>>> {
        TODO("Not yet implemented")
    }

    override fun setCamera(camera: Camera) {
        CoroutineScope(ioDispatcher).launch {
            localSource.insertOrUpdateCameras(listOf(camera))
        }
    }

    override fun getDoorphones():  Flow<Resource<List<Doorphone>>> = networkBoundResource(

        query = {
            localSource.getDoorphones()
        },
        fetch = {
            remoteSource.fetchDoorphones()
        },
        shouldFetch = {queryData-> queryData.isEmpty()},
        saveFetchResult = { camera ->
            localSource.deleteAllDoorphones()
            camera.collectLatest { fetchedResult -> localSource.insertDoorphones(fetchedResult) }

        }

    )

    override fun setDoorphones(doorphone: Doorphone) {
        CoroutineScope(ioDispatcher).launch {
            localSource.insertDoorphones(listOf(doorphone))
        }
    }
}
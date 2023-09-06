package ch.magden.veryverycoolcamviewer.model

import ch.magden.veryverycoolcamviewer.model.entities.Camera
import ch.magden.veryverycoolcamviewer.model.entities.Doorphone
import ch.magden.veryverycoolcamviewer.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface DataRepository {

    fun getRooms() : Flow<Resource<List<String>>>

    fun getCameras() : Flow<Resource<List<Camera>>>

    fun setCamera(camera: Camera)

    fun getDoorphones(): Flow<Resource<List<Doorphone>>>

    fun setDoorphones(doorphone: Doorphone)
}
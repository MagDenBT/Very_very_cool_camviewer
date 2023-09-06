package ch.magden.veryverycoolcamviewer.model

import ch.magden.veryverycoolcamviewer.model.entities.Camera
import ch.magden.veryverycoolcamviewer.model.entities.Doorphone
import ch.magden.veryverycoolcamviewer.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface DataRepository {


    fun getCameras() : StateFlow<Resource<List<Camera>>>

    fun setCamera(camera: Camera)

    fun getDoorphones(): StateFlow<Resource<List<Doorphone>>>

    fun setDoorphones(doorphone: Doorphone)
}
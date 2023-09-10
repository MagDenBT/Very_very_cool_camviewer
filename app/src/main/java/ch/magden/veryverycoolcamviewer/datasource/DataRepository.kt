package ch.magden.veryverycoolcamviewer.datasource

import ch.magden.veryverycoolcamviewer.core.domainmodels.Camera
import ch.magden.veryverycoolcamviewer.core.domainmodels.Doorphone
import ch.magden.veryverycoolcamviewer.core.utils.Resource
import kotlinx.coroutines.flow.StateFlow

interface DataRepository {

    fun getCameras(): StateFlow<Resource<List<Camera>>>

    fun setCamera(camera: Camera)

    fun getDoorphones(): StateFlow<Resource<List<Doorphone>>>

    fun setDoorphones(doorphone: Doorphone)
}

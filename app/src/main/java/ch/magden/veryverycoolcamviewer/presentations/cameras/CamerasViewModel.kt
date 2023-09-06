package ch.magden.veryverycoolcamviewer.presentations.cameras

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.magden.veryverycoolcamviewer.model.DataRepository
import ch.magden.veryverycoolcamviewer.model.entities.Camera
import ch.magden.veryverycoolcamviewer.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CamerasViewModel(private val dataRepository: DataRepository) : ViewModel() {

    private val cameras: Flow<Resource<List<Camera>>> = dataRepository.getCameras()

    val roomsAndCameras = cameras.map { resource ->
        when (resource) {
            is Resource.Loading -> Resource.Loading()
            is Resource.Error -> Resource.Error(resource.error!!)
            is Resource.Success -> Resource.Success(mappingRoomsToCameras(resource))
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, Resource.Loading())

    private fun mappingRoomsToCameras(resource: Resource<List<Camera>>) :  Map<String?, List<Camera>> =
        resource.data!!.let { data -> data.map { camera -> camera.room to data.filter { it.room == camera.room } } }
            .toMap()

    fun switchCameraIsFavorite(camera: Camera) {
        dataRepository.setCamera(camera.copy(isFavorite = !camera.isFavorite))
    }

}
package ch.magden.veryverycoolcamviewer.presentations.cameras

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.magden.veryverycoolcamviewer.model.DataRepository
import ch.magden.veryverycoolcamviewer.model.entities.Camera
import ch.magden.veryverycoolcamviewer.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CamerasViewModel(dataRepository: DataRepository) : ViewModel() {


    private val cameras: StateFlow<Resource<List<Camera>>> = dataRepository.getCameras().stateIn(viewModelScope, SharingStarted.Lazily,Resource.Loading(emptyList()))

    private val rooms: StateFlow<List<String>> = MutableStateFlow(listOf<String>())

    val roomsAndCameras = combine(rooms, cameras) { rooms, cameras ->
        rooms.map { room -> room to cameras.filter { camera -> camera.room == room } }
            .toMutableList()
            .also { it.add("" to cameras.filter { camera -> camera.room.isNullOrBlank() }) }
            .toMap()
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyMap())

    fun switchCameraIsFavorite(cameraId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                val camera =  cameras.value.find {it.id == cameraId }
                camera?.let { it.isFavorite.value = !it.isFavorite.value }
            }
        }
    }

}
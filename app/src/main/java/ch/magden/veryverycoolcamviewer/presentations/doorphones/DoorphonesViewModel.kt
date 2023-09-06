package ch.magden.veryverycoolcamviewer.presentations.doorphones

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.magden.veryverycoolcamviewer.model.DataRepository
import ch.magden.veryverycoolcamviewer.model.entities.Camera
import ch.magden.veryverycoolcamviewer.model.entities.Doorphone
import ch.magden.veryverycoolcamviewer.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DoorphonesViewModel(private val dataRepository: DataRepository)  : ViewModel() {

    val doorphones: StateFlow<Resource<List<Doorphone>>> = dataRepository.getDoorphones()

    fun switchDoorphoneIsFavorite(doorphone: Doorphone) {
        dataRepository.setDoorphones(doorphone.copy(isFavorite = !doorphone.isFavorite))
    }

    fun setDoorphoneName(newName: String, doorphone: Doorphone){
        dataRepository.setDoorphones(doorphone.copy(name = newName))
    }

}
package ch.magden.veryverycoolcamviewer.presentations.doorphones

import androidx.lifecycle.ViewModel
import ch.magden.veryverycoolcamviewer.model.DataRepository
import ch.magden.veryverycoolcamviewer.model.entities.Doorphone
import ch.magden.veryverycoolcamviewer.utils.Resource
import kotlinx.coroutines.flow.StateFlow

class DoorphonesViewModel(private val dataRepository: DataRepository) : ViewModel() {

    val doorphones: StateFlow<Resource<List<Doorphone>>> = dataRepository.getDoorphones()

    fun switchDoorphoneIsFavorite(doorphone: Doorphone) {
        dataRepository.setDoorphones(doorphone.copy(isFavorite = !doorphone.isFavorite))
    }

    fun setDoorphoneName(newName: String, doorphone: Doorphone) {
        dataRepository.setDoorphones(doorphone.copy(name = newName))
    }
}

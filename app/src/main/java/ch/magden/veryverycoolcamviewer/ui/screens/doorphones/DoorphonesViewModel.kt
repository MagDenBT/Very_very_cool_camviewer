package ch.magden.veryverycoolcamviewer.ui.screens.doorphones

import androidx.lifecycle.ViewModel
import ch.magden.veryverycoolcamviewer.core.domainmodels.Doorphone
import ch.magden.veryverycoolcamviewer.core.utils.Resource
import ch.magden.veryverycoolcamviewer.datasource.DataRepository
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

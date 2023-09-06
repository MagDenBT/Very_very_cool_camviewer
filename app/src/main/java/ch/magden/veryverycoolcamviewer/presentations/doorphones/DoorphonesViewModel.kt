package ch.magden.veryverycoolcamviewer.presentations.doorphones

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.magden.veryverycoolcamviewer.model.entities.Doorphone
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DoorphonesViewModel : ViewModel() {

    private val _doorphonesItems = MutableStateFlow(listOf<Doorphone>())
    val doorphonesItems: StateFlow<List<Doorphone>> get() = _doorphonesItems


    fun setDoorphoneName(newName: String, doorphoneId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                doorphonesItems.value.find { it.id == doorphoneId }?.name?.value = newName
            }
        }
    }

    fun switchDoorphoneIsFavorite(doorphoneId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
               val doorphone =  doorphonesItems.value.find {it.id == doorphoneId }
                doorphone?.let { it.isFavorite.value = !it.isFavorite.value }
            }
        }
    }

}
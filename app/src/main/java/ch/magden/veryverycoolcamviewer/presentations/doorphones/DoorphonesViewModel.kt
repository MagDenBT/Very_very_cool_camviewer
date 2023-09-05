package ch.magden.veryverycoolcamviewer.presentations.doorphones

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DoorphonesViewModel : ViewModel() {

    private val _doorphonesItems = MutableStateFlow(listOf<DoorphoneItem>())
    val doorphonesItems: StateFlow<List<DoorphoneItem>> get() = _doorphonesItems


    fun editDoorphoneName(doorphoneId: Int, newName: String) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                doorphonesItems.value.find { it -> it.id == doorphoneId }?.name?.value = newName
            }
        }
    }

}
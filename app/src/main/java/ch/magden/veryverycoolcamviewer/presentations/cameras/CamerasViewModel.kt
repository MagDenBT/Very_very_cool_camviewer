package ch.magden.veryverycoolcamviewer.presentations.cameras

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import ch.magden.veryverycoolcamviewer.presentations.doorphones.DoorphoneItem
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList

class CamerasViewModel : ViewModel() {

    val camerasItems: SnapshotStateMap<MutableState<String>, SnapshotStateList<CameraItem>>? = null
}
package ch.magden.veryverycoolcamviewer.presentations.doorphones

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList

class DoorphonesViewModel: ViewModel() {

    val doorphonesItems: SnapshotStateList<DoorphoneItem>? = null
}
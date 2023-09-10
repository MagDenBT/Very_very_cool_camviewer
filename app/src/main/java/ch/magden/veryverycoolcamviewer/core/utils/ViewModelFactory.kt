package ch.magden.veryverycoolcamviewer.core.utils

import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

typealias ViewModelCreator<VM> = () -> VM

@Suppress("UNCHECKED_CAST")
class ViewModelFactory<VM : ViewModel>(private val creator: ViewModelCreator<VM>) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return creator() as T
    }
}

inline fun <reified VM : ViewModel> ComponentActivity.viewModelCreator(
    noinline creator: ViewModelCreator<VM>
): Lazy<VM> {
    return viewModels { ViewModelFactory(creator) }
}

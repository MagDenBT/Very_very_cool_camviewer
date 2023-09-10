package ch.magden.veryverycoolcamviewer.ui.di

import ch.magden.veryverycoolcamviewer.datasource.DataRepository
import ch.magden.veryverycoolcamviewer.ui.screens.cameras.CamerasViewModel
import ch.magden.veryverycoolcamviewer.ui.screens.doorphones.DoorphonesViewModel
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Subcomponent(modules = [MainActivityModule::class])
interface MainActivityComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): MainActivityComponent
    }

    fun camerasViewModel(): CamerasViewModel

    fun doorphonesViewModel(): DoorphonesViewModel
}

@Module
class MainActivityModule {

    @Provides
    fun provideCamerasViewModel(dataRepository: DataRepository) = CamerasViewModel(dataRepository)

    @Provides
    fun provideDoorphonesViewModel(dataRepository: DataRepository) =
        DoorphonesViewModel(dataRepository)
}

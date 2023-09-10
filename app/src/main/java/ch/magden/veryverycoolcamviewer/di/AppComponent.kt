package ch.magden.veryverycoolcamviewer.di

import ch.magden.veryverycoolcamviewer.datasource.di.DataModule
import ch.magden.veryverycoolcamviewer.ui.di.MainActivityComponent
import dagger.Component
import dagger.Module
import javax.inject.Singleton

@Singleton
@Component( modules = [AppModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(): AppComponent
    }

    fun mainActivityComponent(): MainActivityComponent.Factory
}

@Module(subcomponents = [MainActivityComponent::class], includes = [DataModule::class])
class AppModule


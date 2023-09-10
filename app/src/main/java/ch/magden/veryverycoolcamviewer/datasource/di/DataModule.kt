package ch.magden.veryverycoolcamviewer.datasource.di

import ch.magden.veryverycoolcamviewer.datasource.DataRepository
import ch.magden.veryverycoolcamviewer.datasource.DataRepositoryImpl
import ch.magden.veryverycoolcamviewer.datasource.localdata.LocalData
import ch.magden.veryverycoolcamviewer.datasource.localdata.realm.RealmDb
import ch.magden.veryverycoolcamviewer.datasource.remotedata.RemoteData
import ch.magden.veryverycoolcamviewer.datasource.remotedata.ktor.KtorHttpClient
import dagger.Binds
import dagger.Module
import dagger.Provides
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json

@Module(includes = [DataModuleProviders::class])
abstract class DataModule {

    @Binds
    abstract fun bindLocalData(realmDb: RealmDb): LocalData

    @Binds
    abstract fun bindRemoteData(ktorHttpClient: KtorHttpClient): RemoteData

    @Binds
    abstract fun bindDataRepository(dataRepositoryImpl: DataRepositoryImpl): DataRepository
}

@Module
class DataModuleProviders {

    @Singleton
    @Provides
    fun provideHttpClient(): HttpClient {
        val timeOut = 30_000
        return HttpClient(Android) {
            install(JsonFeature) {
                serializer = KotlinxSerializer(
                    Json {
                        prettyPrint = true
                        ignoreUnknownKeys = true
                        isLenient = true
                        encodeDefaults = false
                    }
                )
            }
            engine {
                connectTimeout = timeOut
                socketTimeout = timeOut
            }
        }
    }

    @Singleton
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO
}

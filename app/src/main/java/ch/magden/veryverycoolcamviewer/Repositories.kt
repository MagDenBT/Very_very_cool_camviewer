package ch.magden.veryverycoolcamviewer

import android.content.Context
import ch.magden.veryverycoolcamviewer.model.DataRepositoryImpl
import ch.magden.veryverycoolcamviewer.model.localsource.realm.REALM_DATABASE_NAME
import ch.magden.veryverycoolcamviewer.model.localsource.realm.REALM_DATABASE_VERSION
import ch.magden.veryverycoolcamviewer.model.localsource.realm.RealmDb
import ch.magden.veryverycoolcamviewer.model.remotesource.ktor.KtorHtttpClient
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.coroutines.Dispatchers

object Repositories {

    private lateinit var dataLocalSource: RealmDb
    private lateinit var dataRemoteSource: KtorHtttpClient
    lateinit var dataRepository: DataRepositoryImpl

    fun init(context: Context) {
        Realm.init(context)
        val config = RealmConfiguration.Builder()
            .name(REALM_DATABASE_NAME)
            .schemaVersion(REALM_DATABASE_VERSION)
            .deleteRealmIfMigrationNeeded()
            .build()

        Realm.setDefaultConfiguration(config)

        dataLocalSource = RealmDb()
        dataRemoteSource = KtorHtttpClient()
        dataRepository = DataRepositoryImpl(
            localSource = dataLocalSource,
            remoteSource = dataRemoteSource,
            Dispatchers.IO
        )
    }
}

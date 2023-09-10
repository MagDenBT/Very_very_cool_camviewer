package ch.magden.veryverycoolcamviewer

import android.content.Context
import ch.magden.veryverycoolcamviewer.datasource.DataRepositoryImpl
import ch.magden.veryverycoolcamviewer.datasource.localdata.realm.REALM_DATABASE_NAME
import ch.magden.veryverycoolcamviewer.datasource.localdata.realm.REALM_DATABASE_VERSION
import ch.magden.veryverycoolcamviewer.datasource.localdata.realm.RealmDb
import ch.magden.veryverycoolcamviewer.datasource.remotedata.ktor.KtorHttpClient
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.coroutines.Dispatchers

object Repositories {

    private lateinit var localData: RealmDb
    private lateinit var remoteData: KtorHttpClient
    lateinit var dataRepository: DataRepositoryImpl

    fun init(context: Context) {
        Realm.init(context)
        val config = RealmConfiguration.Builder()
            .name(REALM_DATABASE_NAME)
            .schemaVersion(REALM_DATABASE_VERSION)
            .deleteRealmIfMigrationNeeded()
            .build()

        Realm.setDefaultConfiguration(config)

        localData = RealmDb()
        remoteData = KtorHttpClient()
        dataRepository = DataRepositoryImpl(
            localData = localData,
            remoteData = remoteData,
            Dispatchers.IO
        )
    }
}

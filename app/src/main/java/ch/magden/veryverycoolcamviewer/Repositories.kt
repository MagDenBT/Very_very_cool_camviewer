package ch.magden.veryverycoolcamviewer

import android.content.Context
import ch.magden.veryverycoolcamviewer.model.localsource.realm.REALM_DATABASE_NAME
import ch.magden.veryverycoolcamviewer.model.localsource.realm.REALM_DATABASE_VERSION
import io.realm.Realm
import io.realm.RealmConfiguration

object Repositories  {

    private lateinit var applicationContext: Context

    fun init(context: Context) {

        Realm.init(applicationContext)
        val config = RealmConfiguration.Builder()
            .name(REALM_DATABASE_NAME)
            .allowQueriesOnUiThread(false)
            .schemaVersion(REALM_DATABASE_VERSION)
            .deleteRealmIfMigrationNeeded()
            .build()

        Realm.setDefaultConfiguration(config)
        applicationContext = context
    }

}
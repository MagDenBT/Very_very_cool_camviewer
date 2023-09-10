package ch.magden.veryverycoolcamviewer

import android.app.Application
import ch.magden.veryverycoolcamviewer.datasource.localdata.realm.REALM_DATABASE_NAME
import ch.magden.veryverycoolcamviewer.datasource.localdata.realm.REALM_DATABASE_VERSION
import ch.magden.veryverycoolcamviewer.di.AppComponent
import ch.magden.veryverycoolcamviewer.di.DaggerAppComponent
import io.realm.Realm
import io.realm.RealmConfiguration

class InitApp : Application() {

    val appComponent: AppComponent by lazy {
        initializeAppComponent()
    }

    private fun initializeAppComponent(): AppComponent {
        configureRealm()
        return DaggerAppComponent.factory().create()
    }

    private fun configureRealm() {
        Realm.init(this)
        val config = RealmConfiguration.Builder()
            .name(REALM_DATABASE_NAME)
            .schemaVersion(REALM_DATABASE_VERSION)
            .deleteRealmIfMigrationNeeded()
            .build()

        Realm.setDefaultConfiguration(config)
    }
}

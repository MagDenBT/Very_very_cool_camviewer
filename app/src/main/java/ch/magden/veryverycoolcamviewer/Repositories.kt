package ch.magden.veryverycoolcamviewer

import android.content.Context

object Repositories  {

    private lateinit var applicationContext: Context

    fun init(context: Context) {
        applicationContext = context
    }

}
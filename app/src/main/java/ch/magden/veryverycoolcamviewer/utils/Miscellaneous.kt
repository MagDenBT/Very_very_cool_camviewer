package ch.magden.veryverycoolcamviewer.utils

import android.content.res.Resources

fun Float.regardingDp(): Float = this * density + 0.5f

val density: Float
    get() = Resources.getSystem().displayMetrics.density

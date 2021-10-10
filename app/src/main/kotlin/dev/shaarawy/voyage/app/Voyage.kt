package dev.shaarawy.voyage.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import logcat.AndroidLogcatLogger
import logcat.LogPriority

/**
 * @author Mohamed Elshaarawy on Oct 06, 2021.
 */
@HiltAndroidApp
class Voyage:Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
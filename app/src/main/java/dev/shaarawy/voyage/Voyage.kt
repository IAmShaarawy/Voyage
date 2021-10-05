package dev.shaarawy.voyage

import android.app.Application
import logcat.AndroidLogcatLogger
import logcat.LogPriority

/**
 * @author Mohamed Elshaarawy on Oct 06, 2021.
 */
class Voyage:Application() {
    override fun onCreate() {
        super.onCreate()
        AndroidLogcatLogger.installOnDebuggableApp(this)
    }
}
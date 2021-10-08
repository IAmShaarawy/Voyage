package dev.shaarawy.voyage.app.initializers

import android.app.Application
import android.content.Context
import androidx.startup.Initializer
import logcat.AndroidLogcatLogger

/**
 * @author Mohamed Elshaarawy on Oct 08, 2021.
 */
class LogCatInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        AndroidLogcatLogger.installOnDebuggableApp(context as Application)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}
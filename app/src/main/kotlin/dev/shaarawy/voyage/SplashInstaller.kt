package dev.shaarawy.voyage

import androidx.activity.ComponentActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @author Mohamed Elshaarawy on Oct 14, 2021.
 */
class SplashInstaller<A : ComponentActivity>(
    private val activity: A,
    visibilityPredicate: () -> Boolean = { BuildConfig.BUILD_TYPE != "debug" },
    private val beforeHide: suspend A.() -> Unit = { delay(2000) },
    private val afterHide: A.() -> Unit
) : CoroutineScope by activity.lifecycleScope {

    private val isSplashVisibleChannel by lazy { Channel<Boolean>() }

    private val isAfterCalled by lazy { Channel<Boolean>(capacity = 1) }

    private val splashSuspensionJob = launch(start = CoroutineStart.LAZY) {
        activity.beforeHide()
        isSplashVisibleChannel.send(false)
    }

    init {
        if (visibilityPredicate()) {
            splashSuspensionJob.start()
            installSplash()
        } else afterSplash()
    }

    private fun installSplash() {
        activity.installSplashScreen().setKeepOnScreenCondition {
            val isVisible = isSplashVisibleChannel.tryReceive().getOrNull() ?: true
            if (!isVisible) {
                afterSplash()
            }
            isVisible
        }
    }

    private fun afterSplash() {
        if (isAfterCalled.tryReceive().getOrNull() != true) {
            isAfterCalled.trySend(true)
            activity.afterHide()
        }
    }
}
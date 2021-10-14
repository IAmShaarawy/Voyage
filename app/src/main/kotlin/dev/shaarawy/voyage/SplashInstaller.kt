package dev.shaarawy.voyage

import androidx.activity.ComponentActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.Duration

/**
 * @author Mohamed Elshaarawy on Oct 14, 2021.
 */
class SplashInstaller<A : ComponentActivity>(
    private val activity: A,
    private val delay: Duration = Duration.ofSeconds(2),
    visibilityPredicate: () -> Boolean = { BuildConfig.BUILD_TYPE != "debug" },
    private val after: A.() -> Unit
) : CoroutineScope by activity.lifecycleScope {

    private val isSplashVisibleChannel by lazy { Channel<Boolean>() }

    private val isAfterCalled by lazy { Channel<Boolean>(capacity = 1) }

    private val delayJob = launch(start = CoroutineStart.LAZY) {
        delay(delay.toMillis())
        isSplashVisibleChannel.send(false)
    }

    init {
        if (visibilityPredicate()) {
            delayJob.start()
            installSplash()
        } else afterSplash()
    }

    private fun installSplash() {
        activity.installSplashScreen().setKeepVisibleCondition {
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
            activity.after()
        }
    }
}
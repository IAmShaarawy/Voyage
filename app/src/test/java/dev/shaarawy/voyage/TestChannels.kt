package dev.shaarawy.voyage

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.channels.produce
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class TestChannels {
    @ExperimentalCoroutinesApi
    @Test
    fun `test channels with multiple receives`() {
        runBlocking {
            val channel = produce<String> {
                listOf(1,2,3,4,5,6).map(Int::toString).forEach {
                    channel.send(it)
                    delay(1000)
                }
            }

            launch {
                for (m in channel) {
                    println("1-> $m")
                }
            }

            launch {
                for (m in channel) {
                    println("2-> $m")
                }
            }

            launch {
                for (m in channel) {
                    println("3-> $m")
                }
            }

        }
        assert(false)
    }
}
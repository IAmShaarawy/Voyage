package dev.shaarawy.voyage.rules

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.mockwebserver.MockWebServer
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import retrofit2.Retrofit

/**
 * @author Mohamed Elshaarawy on Oct 10, 2021.
 */
@ExperimentalSerializationApi
class RetrofitRule(
    private val port: Int = 0,
    private val baseUrl: String = "/",
    private val mediaType: MediaType = "application/json".toMediaType(),
) : TestWatcher() {

    val server by lazy { MockWebServer() }

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(server.url(baseUrl))
            .addConverterFactory(Json.asConverterFactory(mediaType))
            .build()
    }

    override fun starting(description: Description?) {
        super.starting(description)
        server.start(port = port)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        server.shutdown()
    }

    inline fun <reified T> createService(): T = retrofit.create(T::class.java)
}
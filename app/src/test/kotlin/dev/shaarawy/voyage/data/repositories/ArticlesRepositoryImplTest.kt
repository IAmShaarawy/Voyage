package dev.shaarawy.voyage.data.repositories

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dev.shaarawy.voyage.data.services.ArticlesService
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.junit.Test
import retrofit2.Retrofit
import com.google.common.truth.Truth.*
import kotlinx.serialization.ExperimentalSerializationApi
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import java.io.File


/**
 * @author Mohamed Elshaarawy on Oct 10, 2021.
 */
@ExperimentalSerializationApi
class ArticlesRepositoryImplTest {

    private val server by lazy { MockWebServer() }

    private val articlesService by lazy {
        Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build().create(ArticlesService::class.java)
    }

    private val repo by lazy { ArticlesRepositoryImpl(articlesService) }

    @Before
    fun setUp() {
        server.start()
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun `retrieve data`() = runBlocking {
        val r = MockResponse()
        r.setBody(readJSONFile("articles.json"))
        server.enqueue(r)
        assertThat(repo.getArticles(5)).hasSize(5)
    }

    @Test
    fun `not found id`() = runBlocking {
        val r200 = MockResponse().apply { setBody(readJSONFile("article.json")) }
        val r404 = MockResponse().apply { setResponseCode(404) }
        val r500 = MockResponse().apply { setResponseCode(500) }
        server.enqueue(r200)
        assertThat(repo.getArticleById(1)).isNotNull()
        server.enqueue(r404)
        assertThat(repo.getArticleById(0)).isNull()
        server.enqueue(r500)
        val result = runCatching { repo.getArticleById(-1) }
        assertThat(result.exceptionOrNull()).isNotNull()
    }

    private fun readJSONFile(name: String): String {
        val classLoader = javaClass.classLoader!!
        return File(classLoader.getResource(name).file).readText()
    }


}
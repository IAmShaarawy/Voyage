package dev.shaarawy.voyage.data.repositories

import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dev.shaarawy.voyage.readTextFile
import dev.shaarawy.voyage.rules.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import javax.inject.Inject


/**
 * @author Mohamed Elshaarawy on Oct 10, 2021.
 */
@ExperimentalSerializationApi
@ExperimentalCoroutinesApi
@HiltAndroidTest
@RunWith(RobolectricTestRunner::class)
class ArticlesRepositoryImplTest {

    @get:Rule
    val hiltRule by lazy { HiltAndroidRule(this) }

    @get:Rule
    val coroutineRule by lazy { MainCoroutineRule() }

    @Inject
    lateinit var repo: ArticlesRepository

    @Inject
    lateinit var server: MockWebServer

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun `retrieve data`() = runBlocking {
        val r = MockResponse()
        r.setBody(readTextFile("articles/articles.json"))
        server.enqueue(r)
        assertThat(repo.getArticles(5)).hasSize(5)
    }

    @Test
    fun `article by id`() = runBlocking {
        val r200 = MockResponse().apply { setBody(readTextFile("articles/article.json")) }
        server.enqueue(r200)
        assertThat(repo.getArticleById(1)).isNotNull()
    }

    @Test
    fun `article by id not found`() = runBlocking {
        val r404 = MockResponse().apply { setResponseCode(404) }
        server.enqueue(r404)
        assertThat(repo.getArticleById(0)).isNull()
    }

    @Test
    fun `article by id server error`() = runBlocking {
        val r500 = MockResponse().apply { setResponseCode(500) }
        server.enqueue(r500)
        val result = runCatching { repo.getArticleById(-1) }
        assertThat(result.exceptionOrNull()).isNotNull()
    }

    @Test
    fun `test getArticlesPager`() = runBlocking {
        MockResponse().apply {
            setBody(readTextFile("articles/articles.json"))
        }.also {
            server.enqueue(it)
        }
        assertThat(repo.getArticlesPager().firstOrNull()).isNotNull()
    }
}
package dev.shaarawy.voyage.data.repositories

import com.google.common.truth.Truth.assertThat
import dev.shaarawy.voyage.readTextFile
import dev.shaarawy.voyage.rules.MainCoroutineRule
import dev.shaarawy.voyage.rules.RetrofitRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import okhttp3.mockwebserver.MockResponse
import org.junit.Rule
import org.junit.Test


/**
 * @author Mohamed Elshaarawy on Oct 10, 2021.
 */
@ExperimentalSerializationApi
@ExperimentalCoroutinesApi
class ArticlesRepositoryImplTest {

    @get:Rule
    val retrofitRule by lazy { RetrofitRule() }

    @get:Rule
    val coroutineRule by lazy { MainCoroutineRule() }

    private val server by lazy { retrofitRule.server }

    private val repo by lazy { ArticlesRepositoryImpl(retrofitRule.createService()) }

    @Test
    fun `retrieve data`() = runBlocking {
        val r = MockResponse()
        r.setBody(readTextFile("articles.json"))
        server.enqueue(r)
        assertThat(repo.getArticles(5)).hasSize(5)
    }

    @Test
    fun `not found id`() = runBlocking {
        val r200 = MockResponse().apply { setBody(readTextFile("article.json")) }
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
}
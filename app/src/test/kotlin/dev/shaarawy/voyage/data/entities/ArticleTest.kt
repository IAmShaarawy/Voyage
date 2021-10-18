package dev.shaarawy.voyage.data.entities

import com.google.common.truth.Truth.assertThat
import dev.shaarawy.voyage.readJSONFile
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test


/**
 * @author Mohamed Elshaarawy on Oct 08, 2021.
 */
class ArticleTest {

    @Test
    fun `test serialization`() = runBlocking {
        val article = readJSONFile<Article>("articles/article.json")
        assertNotEquals(article.id, -1)
        assertThat(article.events).isNotEmpty()
        assertThat(article.launches).isNotEmpty()
    }

    @Test
    fun `test serialization no id, events, launches, nor featured`() = runBlocking {
        val article = readJSONFile<Article>("articles/article1.json")
        assertEquals(article.id, -1)
        assertThat(article.featured).isFalse()
        assertThat(article.events).isEmpty()
        assertThat(article.launches).isEmpty()
    }
}
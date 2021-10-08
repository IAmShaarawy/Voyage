package dev.shaarawy.voyage.data.entities

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.io.File
import android.R.attr.resource
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import com.google.common.truth.Truth.*


/**
 * @author Mohamed Elshaarawy on Oct 08, 2021.
 */
class ArticleTest {

    @Test
    fun `test serialization`() {
        val article = readSerializedJsonString("article.json")
        assertNotEquals(article.id, -1)
        assertThat(article.events).isNotEmpty()
        assertThat(article.launches).isNotEmpty()
    }

    @Test
    fun `test serialization no id, events, launches, nor featured`() {
        val article = readSerializedJsonString("article1.json")
        assertEquals(article.id, -1)
        assertThat(article.featured).isFalse()
        assertThat(article.events).isEmpty()
        assertThat(article.launches).isEmpty()
    }

    private fun readSerializedJsonString(fileName: String): Article {
        val classLoader = javaClass.classLoader!!
        val jsonString = File(classLoader.getResource(fileName).file).readText()
        return Json.decodeFromString<Article>(jsonString)
    }
}
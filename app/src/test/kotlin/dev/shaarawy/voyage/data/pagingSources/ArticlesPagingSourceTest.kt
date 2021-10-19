package dev.shaarawy.voyage.data.pagingSources

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingSource.LoadParams.Refresh
import androidx.paging.PagingState
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dev.shaarawy.voyage.data.entities.Article
import dev.shaarawy.voyage.data.repositories.ArticlesRepository
import dev.shaarawy.voyage.readJSONFile
import dev.shaarawy.voyage.readTextFile
import dev.shaarawy.voyage.rules.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import retrofit2.HttpException
import javax.inject.Inject

/**
 * @author Mohamed Elshaarawy on Oct 17, 2021.
 */
@ExperimentalSerializationApi
@ExperimentalCoroutinesApi
@HiltAndroidTest
@RunWith(RobolectricTestRunner::class)
class ArticlesPagingSourceTest {
    @get:Rule
    val hiltRule by lazy { HiltAndroidRule(this) }

    @get:Rule
    val coroutineRule by lazy { MainCoroutineRule() }


    @Inject
    lateinit var articlesRepository: ArticlesRepository

    @Inject
    lateinit var server: MockWebServer

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun `test page1`() = runBlocking {
        server.enqueue(MockResponse().apply { setBody(readTextFile("articles/pagination/page1.json")) })
        assertThat(
            ArticlesPagingSource(articlesRepository).load(
                Refresh(
                    key = 0,
                    loadSize = 10,
                    placeholdersEnabled = false
                )
            )
        ).isEqualTo(
            PagingSource.LoadResult.Page(
                data = readJSONFile<List<Article>>("articles/pagination/page1.json"),
                prevKey = null,
                nextKey = 10
            )
        )
    }

    @Test
    fun `test page2`() = runBlocking {
        server.enqueue(MockResponse().apply { setBody(readTextFile("articles/pagination/page2.json")) })
        assertThat(
            ArticlesPagingSource(articlesRepository).load(
                Refresh(
                    key = 10,
                    loadSize = 10,
                    placeholdersEnabled = false
                )
            )
        ).isEqualTo(
            PagingSource.LoadResult.Page(
                data = readJSONFile<List<Article>>("articles/pagination/page2.json"),
                prevKey = 0,
                nextKey = 20
            )
        )
    }

    @Test
    fun `test page3`() = runBlocking {
        server.enqueue(MockResponse().apply { setBody(readTextFile("articles/pagination/page3.json")) })
        assertThat(
            ArticlesPagingSource(articlesRepository).load(
                Refresh(
                    key = 20,
                    loadSize = 10,
                    placeholdersEnabled = false
                )
            )
        ).isEqualTo(
            PagingSource.LoadResult.Page(
                data = readJSONFile<List<Article>>("articles/pagination/page3.json"),
                prevKey = 10,
                nextKey = null
            )
        )
    }

    @Test
    fun `test error`() = runBlocking {
        server.enqueue(MockResponse().apply { setResponseCode(500) })
        val actual = ArticlesPagingSource(articlesRepository).load(
            Refresh(
                key = 20,
                loadSize = 10,
                placeholdersEnabled = false
            )
        )
        assertThat(actual).isInstanceOf(PagingSource.LoadResult.Error::class.java)
        val actualError = actual as PagingSource.LoadResult.Error
        assertThat(actualError.throwable).isInstanceOf(HttpException::class.java)
    }

    @Test
    fun `test getRefreshKey`() = runBlocking {
        val pages = listOf(
            PagingSource.LoadResult.Page(
                data = readJSONFile("articles/pagination/page1.json"),
                prevKey = null,
                nextKey = 10
            ),
            PagingSource.LoadResult.Page(
                data = readJSONFile("articles/pagination/page2.json"),
                prevKey = 0,
                nextKey = 20
            ),
            PagingSource.LoadResult.Page(
                data = readJSONFile<List<Article>>("articles/pagination/page3.json"),
                prevKey = 10,
                nextKey = null
            )
        )
        val pageSource = ArticlesPagingSource(articlesRepository)
        val state1 = PagingState(pages, null, PagingConfig(10), 5)
        assertThat(pageSource.getRefreshKey(state = state1)).isNull()
        val state2 = PagingState(pages, 5, PagingConfig(10), 5)
        assertThat(pageSource.getRefreshKey(state = state2)).isEqualTo(0)
        val state3 = PagingState(pages, 25, PagingConfig(10), 5)
        assertThat(pageSource.getRefreshKey(state = state3)).isEqualTo(20)

    }
}
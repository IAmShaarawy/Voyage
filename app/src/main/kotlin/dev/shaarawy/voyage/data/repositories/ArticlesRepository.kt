package dev.shaarawy.voyage.data.repositories

import dev.shaarawy.voyage.data.entities.Article
import dev.shaarawy.voyage.data.services.ArticlesService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

/**
 * @author Mohamed Elshaarawy on Oct 09, 2021.
 */
interface ArticlesRepository {
    suspend fun getArticlesCount(): Int
    suspend fun getArticles(limit: Int, startFrom: Int? = null): List<Article>
    suspend fun getArticlesByLaunchId(launchId: String): List<Article>
    suspend fun getArticlesByEventId(eventId: Int): List<Article>
    suspend fun getArticleById(id: Int): Article?
}

class ArticlesRepositoryImpl @Inject constructor(
    private val service: ArticlesService,
) : ArticlesRepository {

    override suspend fun getArticlesCount() = withContext(Dispatchers.IO) {
        service.getCount().toInt()
    }

    override suspend fun getArticles(limit: Int, startFrom: Int?) = withContext(Dispatchers.IO) {
        val queryParameter = mutableMapOf<String, String>().apply {
            put("_limit", limit.toString())
            if (startFrom != null)
                put("_start", startFrom.toString())
        }
        service.getArticles(parameters = queryParameter)
    }

    override suspend fun getArticlesByLaunchId(launchId: String) = withContext(Dispatchers.IO) {
        service.getArticles(launchId)
    }

    override suspend fun getArticlesByEventId(eventId: Int) = withContext(Dispatchers.IO) {
        service.getArticles(eventId)
    }

    override suspend fun getArticleById(id: Int) = withContext(Dispatchers.IO) {
        try {
            service.getArticle(id)
        } catch (error: HttpException) {
            if (error.code() == 404)
                null
            else throw error
        }
    }
}
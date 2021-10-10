package dev.shaarawy.voyage.data.services

import dev.shaarawy.voyage.data.entities.Article
import retrofit2.http.*

/**
 * @author Mohamed Elshaarawy on Oct 09, 2021.
 */
interface ArticlesService {
    @GET("articles/count")
    suspend fun getCount(): String

    @GET("articles")
    suspend fun getArticles(@QueryMap parameters: Map<String, String>): List<Article>

    @GET("articles/{id}")
    suspend fun getArticle(@Path("id") id: Int): Article

    @GET("articles/launch/{id}")
    suspend fun getArticles(@Path("id") launchId: String): List<Article>

    @GET("articles/event/{id}")
    suspend fun getArticles(@Path("id") eventId: Int): List<Article>
}
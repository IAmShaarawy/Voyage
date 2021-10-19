package dev.shaarawy.voyage.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.shaarawy.voyage.data.pagingSources.ArticlesPagingSource
import dev.shaarawy.voyage.data.repositories.ArticlesRepository

/**
 * @author Mohamed Elshaarawy on Oct 19, 2021.
 */
@Deprecated(
    message = "It is recommended to construct PagingSource manually",
    level = DeprecationLevel.ERROR
)
@Module
@InstallIn(SingletonComponent::class)
class PagingSourceModule {

    @Provides
    fun provideArticlesPagingSource(articlesRepository: ArticlesRepository): ArticlesPagingSource =
        throw IllegalStateException("You must construct PagingSource manually")
}
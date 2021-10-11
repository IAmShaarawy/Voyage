package dev.shaarawy.voyage.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.shaarawy.voyage.data.services.ArticlesService
import retrofit2.Retrofit

/**
 * @author Mohamed Elshaarawy on Oct 12, 2021.
 */
@Module
@InstallIn(SingletonComponent::class)
class ServicesModule {
    @Provides
    fun provideArticleService(retrofit: Retrofit): ArticlesService =
        retrofit.create(ArticlesService::class.java)
}
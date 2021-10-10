package dev.shaarawy.voyage.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.shaarawy.voyage.data.repositories.ArticlesRepository
import dev.shaarawy.voyage.data.repositories.ArticlesRepositoryImpl

/**
 * @author Mohamed Elshaarawy on Oct 10, 2021.
 */
@Module
@InstallIn(SingletonComponent::class)
interface RepositoriesModule {

    @Binds
    fun bindArticleRepo(repository: ArticlesRepositoryImpl): ArticlesRepository
}
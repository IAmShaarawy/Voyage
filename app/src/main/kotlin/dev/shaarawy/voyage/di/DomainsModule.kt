package dev.shaarawy.voyage.di

import dagger.Module
import dagger.hilt.DefineComponent
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

/**
 * @author Mohamed Elshaarawy on Oct 10, 2021.
 */
@Module()
@InstallIn(ViewModelComponent::class)
class DomainsModule
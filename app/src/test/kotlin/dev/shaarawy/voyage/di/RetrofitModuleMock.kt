package dev.shaarawy.voyage.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * @author Mohamed Elshaarawy on Oct 12, 2021.
 */
@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RetrofitModule::class]
)
class RetrofitModuleMock {

    @Singleton
    @Provides
    fun mockWebServer() = MockWebServer()

    @ExperimentalSerializationApi
    @Singleton
    @Provides
    fun provideRetrofit(server: MockWebServer): Retrofit = Retrofit.Builder()
        .baseUrl(server.url("/"))
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .build()


}
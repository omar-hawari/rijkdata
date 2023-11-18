package com.omarhawari.rijksdata.di

import com.omarhawari.rijksdata.data.RijkApi
import com.omarhawari.rijksdata.data.RijkRepositoryNetwork
import com.omarhawari.rijksdata.domain.RijkRepositoryContract
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "https://www.rijksmuseum.nl/api/nl/"

    @Provides
    @Singleton
    fun provideApiService(): RijkApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    })
                    .build()
            )
            .build()
            .create(RijkApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRijkRepositoryContract(api: RijkApi): RijkRepositoryContract =
        RijkRepositoryNetwork(api = api)

}
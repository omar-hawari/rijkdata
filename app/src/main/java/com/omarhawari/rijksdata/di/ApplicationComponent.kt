package com.omarhawari.rijksdata.di

import com.omarhawari.rijksdata.data.RijkApi
import dagger.Component
import dagger.Provides
import javax.inject.Singleton

@Singleton
@Component
object ApplicationComponent  {

    @Provides
    @Singleton
    fun provideApiService(): RijkApi {
        return ApiClient.retrofit.create(RijkApi::class.java)
    }
}

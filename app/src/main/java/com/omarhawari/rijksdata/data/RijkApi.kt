package com.omarhawari.rijksdata.data

import com.omarhawari.rijksdata.BuildConfig
import com.omarhawari.rijksdata.data.resource.ArtObjectDetailsResource
import com.omarhawari.rijksdata.data.resource.ArtObjectDetailsResponseResource
import com.omarhawari.rijksdata.data.resource.ArtObjectsListResponseResource
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RijkApi {

    @GET("collection")
    suspend fun getArtObjects(
        @Query("key") key: String = BuildConfig.API_KEY,
        @Query("p") pageIndex: Int,
        @Query("ps") pageSize: Int,
    ): ArtObjectsListResponseResource

    @GET("collection/{objectNumber}")
    suspend fun getArtObjectsDetails(
        @Path("objectNumber") objectNumber: String,
        @Query("key") key: String = BuildConfig.API_KEY,
    ): ArtObjectDetailsResponseResource

}
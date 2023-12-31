package com.omarhawari.rijksdata.domain

import com.omarhawari.rijksdata.core.DataResult
import com.omarhawari.rijksdata.domain.models.ArtObject
import com.omarhawari.rijksdata.domain.models.ArtObjectDetails

interface RijkRepositoryContract {

    // Int here is Page Index
    suspend fun getArtObjects(pageIndex: Int, pageSize: Int, sortBy: String?): DataResult<Pair<Int, List<ArtObject>>>

    suspend fun getArtObjectDetails(objectNumber: String): DataResult<ArtObjectDetails>

}
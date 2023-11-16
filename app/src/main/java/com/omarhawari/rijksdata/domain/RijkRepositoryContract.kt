package com.omarhawari.rijksdata.domain

import com.omarhawari.rijksdata.core.DataResult
import com.omarhawari.rijksdata.data.resource.ArtObjectResource
import com.omarhawari.rijksdata.domain.models.ArtObject
import com.omarhawari.rijksdata.domain.models.ArtObjectDetails

interface RijkRepositoryContract {

    // Int here is Page Index
    fun getArtObjects(pageIndex: Int, pageSize: Int): DataResult<Pair<Int, List<ArtObject>>>

    fun getArtObjectDetails(objectName: String): DataResult<ArtObjectDetails>

}
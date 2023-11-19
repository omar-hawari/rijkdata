package com.omarhawari.rijksdata.data

import com.omarhawari.rijksdata.core.asDataResult
import com.omarhawari.rijksdata.domain.RijkRepositoryContract
import com.omarhawari.rijksdata.domain.mappers.toDomain
import javax.inject.Inject

class RijkRepositoryNetwork @Inject constructor(private val api: RijkApi) : RijkRepositoryContract {
    override suspend fun getArtObjects(pageIndex: Int, pageSize: Int, sortBy: String?) = asDataResult {
        Pair(
            pageIndex,
            api.getArtObjects(
                pageIndex = pageIndex,
                pageSize = pageSize,
                sortBy = sortBy
            ).artObjects.map { it.toDomain() })
    }

    override suspend fun getArtObjectDetails(objectNumber: String) = asDataResult {
        api.getArtObjectsDetails(objectNumber).artObject.toDomain()
    }
}
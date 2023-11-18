package com.omarhawari.rijksdata.data

import com.omarhawari.rijksdata.core.asDataResult
import com.omarhawari.rijksdata.domain.RijkRepositoryContract
import com.omarhawari.rijksdata.domain.mappers.toDomain
import javax.inject.Inject

class RijkRepositoryNetwork @Inject constructor(private val api: RijkApi) : RijkRepositoryContract {
    override suspend fun getArtObjects(pageIndex: Int, pageSize: Int) = asDataResult {
        Pair(
            pageIndex,
            api.getArtObjects(
                pageIndex = pageIndex,
                pageSize = pageSize
            ).artObjects.map { it.toDomain() })
    }

    override suspend fun getArtObjectDetails(objectName: String) = asDataResult {
        api.getArtObjectsDetails(objectName).artObject.toDomain()
    }
}
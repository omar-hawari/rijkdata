package com.omarhawari.rijksdata.domain.usecases

import com.omarhawari.rijksdata.core.DataResult
import com.omarhawari.rijksdata.domain.RijkRepositoryContract
import com.omarhawari.rijksdata.domain.models.ArtObject
import javax.inject.Inject

class GetArtObjectListUseCase @Inject constructor(private val repository: RijkRepositoryContract) {

    suspend operator fun invoke(
        pageIndex: Int,
        pageSize: Int,
        sortBy: String?
    ) = repository.getArtObjects(pageIndex, pageSize, sortBy)

    companion object {
        const val SORT_BY_ARTIST = "artist"
    }

}
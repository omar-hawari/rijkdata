package com.omarhawari.rijksdata.domain.usecases

import com.omarhawari.rijksdata.domain.RijkRepositoryContract
import com.omarhawari.rijksdata.presentation.art_objects_list.ArtObjectListViewModel
import javax.inject.Inject

class GetArtObjectListUseCase @Inject constructor(private val repository: RijkRepositoryContract) {

    suspend operator fun invoke(
        pageIndex: Int,
        pageSize: Int,
        sortBy: ArtObjectListViewModel.SortBy?
    ) = repository.getArtObjects(pageIndex, pageSize, when (sortBy) {
        ArtObjectListViewModel.SortBy.ARTIST -> SORT_BY_ARTIST
        ArtObjectListViewModel.SortBy.ARTIST_DESC -> SORT_BY_ARTIST_DESC
        ArtObjectListViewModel.SortBy.RELEVANCE -> SORT_BY_RELEVANCE
        ArtObjectListViewModel.SortBy.OBJECT_TYPE -> SORT_BY_OBJECT_TYPE
        ArtObjectListViewModel.SortBy.CHRONOLOGIC -> SORT_BY_CHRONOLOGIC
        ArtObjectListViewModel.SortBy.ACHRONOLOGIC -> SORT_BY_ACHRONOLOGIC
        null -> null
    })

    companion object {
        const val SORT_BY_ARTIST = "artist"
        const val SORT_BY_ARTIST_DESC = "artistdesc"
        const val SORT_BY_RELEVANCE = "relevance"
        const val SORT_BY_OBJECT_TYPE = "objecttype"
        const val SORT_BY_CHRONOLOGIC = "chronologic"
        const val SORT_BY_ACHRONOLOGIC = "achronologic"
    }

}
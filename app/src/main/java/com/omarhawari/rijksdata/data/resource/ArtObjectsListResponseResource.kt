package com.omarhawari.rijksdata.data.resource

data class ArtObjectsListResponseResource(
    val elapsedMilliseconds: Long,
    val count: Int,
    val countFacets: CountFacets,
    val artObjects: List<ArtObjectResource>
) {
    data class CountFacets(
        val hasimage: Int,
        val ondisplay: Int
    )

}
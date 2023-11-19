package com.omarhawari.rijksdata.data.resource

import androidx.annotation.Keep

@Keep
data class ArtObjectResource(
    val links: LinksResource,
    val id: String,
    val objectNumber: String,
    val title: String,
    val hasImage: Boolean,
    val principalOrFirstMaker: String,
    val longTitle: String,
    val showImage: Boolean,
    val permitDownload: Boolean,
    val webImage: ImageResource?,
    val headerImage: ImageResource?,
    val productionPlaces: List<String>
) {
    data class LinksResource(
        val self: String,
        val web: String
    )

    data class ImageResource(
        val guid: String?,
        val offsetPercentageX: Int,
        val offsetPercentageY: Int,
        val width: Int,
        val height: Int,
        val url: String
    )

}
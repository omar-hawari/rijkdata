package com.omarhawari.rijksdata.domain.models

import androidx.annotation.Keep

@Keep
data class ArtObject(
    val links: Links,
    val id: String,
    val objectNumber: String,
    val title: String,
    val hasImage: Boolean,
    val principalOrFirstMaker: String,
    val longTitle: String,
    val showImage: Boolean,
    val permitDownload: Boolean,
    val webImage: Image,
    val headerImage: Image,
    val productionPlaces: List<String>
) {
    data class Links(
        val self: String,
        val web: String
    )

    data class Image(
        val guid: String,
        val offsetPercentageX: Int,
        val offsetPercentageY: Int,
        val width: Int,
        val height: Int,
        val url: String
    )

}
package com.omarhawari.rijksdata.domain.mappers

import com.omarhawari.rijksdata.data.resource.ArtObjectResource
import com.omarhawari.rijksdata.domain.models.ArtObject

fun ArtObjectResource.toDomain() = ArtObject(
    links = links.toDomain(),
    id = id,
    objectNumber = objectNumber,
    title = title,
    hasImage = hasImage,
    principalOrFirstMaker = principalOrFirstMaker,
    longTitle = longTitle,
    showImage = showImage,
    permitDownload = permitDownload,
    webImage = webImage.toDomain(),
    headerImage = headerImage.toDomain(),
    productionPlaces = productionPlaces
)

fun ArtObjectResource.LinksResource.toDomain() = ArtObject.Links(self, web)

fun ArtObjectResource.ImageResource.toDomain() =
    ArtObject.Image(guid, offsetPercentageX, offsetPercentageY, width, height, url)
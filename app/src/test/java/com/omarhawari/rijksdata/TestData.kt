package com.omarhawari.rijksdata

import com.omarhawari.rijksdata.domain.models.ArtObject

object TestData {


    val links = ArtObject.Links(
        self = "https://example.com/self",
        web = "https://example.com/web"
    )

    val webImage = ArtObject.Image(
        guid = "123456",
        offsetPercentageX = 10,
        offsetPercentageY = 20,
        width = 800,
        height = 600,
        url = "https://example.com/image.jpg"
    )

    val headerImage = ArtObject.Image(
        guid = "789012",
        offsetPercentageX = 5,
        offsetPercentageY = 15,
        width = 1200,
        height = 900,
        url = "https://example.com/header.jpg"
    )

    val productionPlaces = listOf("Place1", "Place2", "Place3")

    val artObject = ArtObject(
        links = links,
        id = "123",
        objectNumber = "ABC123",
        title = "Example Art Object",
        hasImage = true,
        principalOrFirstMaker = "Artist Name",
        longTitle = "A long title for the art object",
        showImage = true,
        permitDownload = true,
        webImage = webImage,
        headerImage = headerImage,
        productionPlaces = productionPlaces
    )


}
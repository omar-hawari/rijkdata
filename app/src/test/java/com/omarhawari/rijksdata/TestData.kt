package com.omarhawari.rijksdata

import com.omarhawari.rijksdata.domain.models.ArtObject
import com.omarhawari.rijksdata.domain.models.ArtObjectDetails

object TestData {


    private val links = ArtObject.Links(
        self = "https://example.com/self",
        web = "https://example.com/web"
    )

    private val webImage = ArtObject.Image(
        guid = "123456",
        offsetPercentageX = 10,
        offsetPercentageY = 20,
        width = 800,
        height = 600,
        url = "https://example.com/image.jpg"
    )

    private val headerImage = ArtObject.Image(
        guid = "789012",
        offsetPercentageX = 5,
        offsetPercentageY = 15,
        width = 1200,
        height = 900,
        url = "https://example.com/header.jpg"
    )

    private val productionPlaces = listOf("Place1", "Place2", "Place3")

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

    val artObjectDetails = ArtObjectDetails(
        links = ArtObjectDetails.Links(search = "example search link"),
        id = "123456",
        priref = "789012",
        objectNumber = "ABC123",
        language = "English",
        title = "Example Art Object",
        copyrightHolder = "Example Copyright Holder",
        webImage = ArtObjectDetails.Image(
            guid = "image-guid",
            offsetPercentageX = 10,
            offsetPercentageY = 20,
            width = 800,
            height = 600,
            url = "https://example.com/image.jpg"
        ),
        titles = listOf("Title 1", "Title 2"),
        description = "Example description",
        labelText = "Example label text",
        objectTypes = listOf("Type 1", "Type 2"),
        objectCollection = listOf("Collection 1", "Collection 2"),
        makers = listOf(
            ArtObjectDetails.Maker(
                name = "Maker 1",
                unFixedName = "Maker 1",
                placeOfBirth = "Birthplace 1",
                dateOfBirth = "Birthdate 1",
                dateOfBirthPrecision = "Day",
                dateOfDeath = "Deathdate 1",
                dateOfDeathPrecision = "Day",
                placeOfDeath = "Deathplace 1",
                occupation = listOf("Occupation 1"),
                roles = listOf("Role 1"),
                nationality = "Nationality 1",
                biography = "Biography 1",
                productionPlaces = listOf("Production Place 1"),
                qualification = "Qualification 1"
            )
        ),
        principalMakers = emptyList(),
        plaqueDescriptionDutch = "Dutch plaque description",
        plaqueDescriptionEnglish = "English plaque description",
        principalMaker = "Principal Maker",
        artistRole = "Artist Role",
        associations = listOf("Association 1", "Association 2"),
        acquisition = ArtObjectDetails.Acquisition(
            method = "Purchase",
            date = "2023-01-01",
            creditLine = "Example credit line"
        ),
        exhibitions = listOf("Exhibition 1", "Exhibition 2"),
        materials = listOf("Material 1", "Material 2"),
        techniques = listOf("Technique 1", "Technique 2"),
        productionPlaces = listOf("Production Place 1", "Production Place 2"),
        dating = ArtObjectDetails.Dating(
            presentingDate = "2023",
            sortingDate = 2023,
            period = 21,
            yearEarly = 2022,
            yearLate = 2024
        ),
        classification = ArtObjectDetails.Classification(
            iconClassIdentifier = listOf("Icon Class 1", "Icon Class 2")
        ),
        hasImage = true,
        historicalPersons = listOf("Person 1", "Person 2"),
        inscriptions = listOf("Inscription 1", "Inscription 2"),
        documentation = listOf("Documentation 1", "Documentation 2"),
        catRefRPK = listOf("Cat Ref 1", "Cat Ref 2"),
        principalOrFirstMaker = "Principal or First Maker",
        dimensions = listOf(
            ArtObjectDetails.Dimensions(
                unit = "cm",
                type = "Height",
                part = null,
                value = "100"
            )
        ),
        physicalProperties = listOf("Property 1", "Property 2"),
        physicalMedium = "Oil on Canvas",
        longTitle = "Long Title Example",
        subTitle = "Subtitle Example",
        scLabelLine = "Label Line Example",
        label = ArtObjectDetails.Label(
            title = "Label Title",
            makerLine = "Maker Line",
            description = "Label Description",
            notes = "Label Notes",
            date = "Label Date"
        ),
        showImage = true,
        location = "Example Location"
    )


}
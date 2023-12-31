package com.omarhawari.rijksdata.domain.models


data class ArtObjectDetails(
    val links: Links,
    val id: String,
    val priref: String,
    val objectNumber: String,
    val language: String,
    val title: String,
    val copyrightHolder: String?,
    val webImage: Image?,
    val titles: List<String>,
    val description: String?,
    val labelText: String?,
    val objectTypes: List<String>,
    val objectCollection: List<String>,
    val makers: List<Maker>,
    val principalMakers: List<Maker>,
    val plaqueDescriptionDutch: String?,
    val plaqueDescriptionEnglish: String?,
    val principalMaker: String,
    val artistRole: String?,
    val associations: List<String>,
    val acquisition: Acquisition,
    val exhibitions: List<String>,
    val materials: List<String>,
    val techniques: List<String>,
    val productionPlaces: List<String>,
    val dating: Dating,
    val classification: Classification,
    val hasImage: Boolean,
    val historicalPersons: List<String>,
    val inscriptions: List<String>,
    val documentation: List<String>,
    val catRefRPK: List<String>,
    val principalOrFirstMaker: String,
    val dimensions: List<Dimensions>,
    val physicalProperties: List<String>,
    val physicalMedium: String,
    val longTitle: String,
    val subTitle: String,
    val scLabelLine: String,
    val label: Label,
    val showImage: Boolean,
    val location: String?
) {

    data class Links(
        val search: String
    )

    data class Dimensions(
        val unit: String,
        val type: String,
        val part: String?,
        val value: String
    )

    data class
    Image(
        val guid: String,
        val offsetPercentageX: Int,
        val offsetPercentageY: Int,
        val width: Int,
        val height: Int,
        val url: String
    )

    data class Label(
        val title: String?,
        val makerLine: String?,
        val description: String?,
        val notes: String?,
        val date: String?
    )

    data class Acquisition(
        val method: String,
        val date: String,
        val creditLine: String?
    )

    data class Color(
        val percentage: Int,
        val hex: String
    )

    data class ColorNormalization(
        val originalHex: String,
        val normalizedHex: String
    )

    data class Dating(
        val presentingDate: String,
        val sortingDate: Int,
        val period: Int,
        val yearEarly: Int,
        val yearLate: Int
    )

    data class Classification(
        val iconClassIdentifier: List<String>
    )

    data class Maker(
        val name: String?,
        val unFixedName: String?,
        val placeOfBirth: String?,
        val dateOfBirth: String?,
        val dateOfBirthPrecision: String?,
        val dateOfDeath: String?,
        val dateOfDeathPrecision: String?,
        val placeOfDeath: String?,
        val occupation: List<String>?,
        val roles: List<String>?,
        val nationality: String?,
        val biography: String?,
        val productionPlaces: List<String>?,
        val qualification: String?
    )

}


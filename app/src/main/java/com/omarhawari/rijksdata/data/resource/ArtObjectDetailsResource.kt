package com.omarhawari.rijksdata.data.resource


data class ArtObjectDetailsResource(
    val links: LinksResource,
    val id: String,
    val priref: String,
    val objectNumber: String,
    val language: String,
    val title: String,
    val copyrightHolder: String?,
    val webImage: ImageResource,
    val colors: List<ColorResource>,
    val colorsWithNormalization: List<ColorNormalizationResource>,
    val normalizedColors: List<ColorResource>,
    val normalized32Colors: List<ColorResource>,
    val titles: List<String>,
    val description: String,
    val labelText: String?,
    val objectTypes: List<String>,
    val objectCollection: List<String>,
    val makers: List<String>,
    val principalMakers: List<PrincipalMakerResource>,
    val plaqueDescriptionDutch: String,
    val plaqueDescriptionEnglish: String,
    val principalMaker: String,
    val artistRole: String?,
    val associations: List<String>,
    val acquisition: AcquisitionResource,
    val exhibitions: List<String>,
    val materials: List<String>,
    val techniques: List<String>,
    val productionPlaces: List<String>,
    val dating: DatingResource,
    val classification: ClassificationResource,
    val hasImage: Boolean,
    val historicalPersons: List<String>,
    val inscriptions: List<String>,
    val documentation: List<String>,
    val catRefRPK: List<String>,
    val principalOrFirstMaker: String,
    val dimensions: List<DimensionResource>,
    val physicalProperties: List<String>,
    val physicalMedium: String,
    val longTitle: String,
    val subTitle: String,
    val scLabelLine: String,
    val label: LabelResource,
    val showImage: Boolean,
    val location: String
) {

    data class LinksResource(
        val search: String
    )

    data class DimensionResource(
        val unit: String,
        val type: String,
        val part: String?,
        val value: String
    )

    data class ImageResource(
        val guid: String,
        val offsetPercentageX: Int,
        val offsetPercentageY: Int,
        val width: Int,
        val height: Int,
        val url: String
    )

    data class LabelResource(
        val title: String,
        val makerLine: String,
        val description: String,
        val notes: String,
        val date: String
    )

    data class AcquisitionResource(
        val method: String,
        val date: String,
        val creditLine: String
    )

    data class ColorResource(
        val percentage: Int,
        val hex: String
    )

    data class ColorNormalizationResource(
        val originalHex: String,
        val normalizedHex: String
    )

    data class DatingResource(
        val presentingDate: String,
        val sortingDate: Int,
        val period: Int,
        val yearEarly: Int,
        val yearLate: Int
    )

    data class ClassificationResource(
        val iconClassIdentifier: List<String>
    )

    data class PrincipalMakerResource(
        val name: String,
        val unFixedName: String,
        val placeOfBirth: String,
        val dateOfBirth: String,
        val dateOfBirthPrecision: String?,
        val dateOfDeath: String,
        val dateOfDeathPrecision: String?,
        val placeOfDeath: String,
        val occupation: List<String>,
        val roles: List<String>,
        val nationality: String,
        val biography: String?,
        val productionPlaces: List<String>,
        val qualification: String?
    )

}


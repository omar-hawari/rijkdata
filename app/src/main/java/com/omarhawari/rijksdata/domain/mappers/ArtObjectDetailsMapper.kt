package com.omarhawari.rijksdata.domain.mappers

import com.omarhawari.rijksdata.data.resource.ArtObjectDetailsResource
import com.omarhawari.rijksdata.domain.models.ArtObjectDetails

fun ArtObjectDetailsResource.toDomain() = ArtObjectDetails(
    links = links.toDomain(),
    id = id,
    priref = priref,
    objectNumber = objectNumber,
    language = language,
    title = title,
    copyrightHolder = copyrightHolder,
    webImage = webImage?.toDomain(),
    titles = titles,
    description = description,
    labelText = labelText,
    objectTypes = objectTypes,
    objectCollection = objectCollection,
    makers = makers.map { it.toDomain() },
    principalMakers = principalMakers.map { it.toDomain() },
    plaqueDescriptionDutch = plaqueDescriptionDutch,
    plaqueDescriptionEnglish = plaqueDescriptionEnglish,
    principalMaker = principalMaker,
    artistRole = artistRole,
    associations = associations,
    acquisition = acquisition.toDomain(),
    exhibitions = exhibitions,
    materials = materials,
    techniques = techniques,
    productionPlaces = productionPlaces,
    dating = dating.toDomain(),
    classification = classification.toDomain(),
    hasImage = hasImage,
    historicalPersons = historicalPersons,
    inscriptions = inscriptions,
    documentation = documentation,
    catRefRPK = catRefRPK,
    principalOrFirstMaker = principalOrFirstMaker,
    dimensions = dimensions.map { it.toDomain() },
    physicalProperties = physicalProperties,
    physicalMedium = physicalMedium,
    longTitle = longTitle,
    subTitle = subTitle,
    scLabelLine = scLabelLine,
    label = label.toDomain(),
    showImage = showImage,
    location = location
)


fun ArtObjectDetailsResource.LinksResource.toDomain() = ArtObjectDetails.Links(search)

fun ArtObjectDetailsResource.DimensionResource.toDomain() =
    ArtObjectDetails.Dimensions(unit, type, part, value)

fun ArtObjectDetailsResource.ImageResource.toDomain() =
    ArtObjectDetails.Image(guid, offsetPercentageX, offsetPercentageY, width, height, url)

fun ArtObjectDetailsResource.LabelResource.toDomain() = ArtObjectDetails.Label(title, makerLine, description, notes, date)

fun ArtObjectDetailsResource.AcquisitionResource.toDomain() = ArtObjectDetails.Acquisition(method, date, creditLine)

fun ArtObjectDetailsResource.DatingResource.toDomain() = ArtObjectDetails.Dating(presentingDate, sortingDate, period, yearEarly, yearLate)

fun ArtObjectDetailsResource.ClassificationResource.toDomain() = ArtObjectDetails.Classification(iconClassIdentifier)

fun ArtObjectDetailsResource.MakerResource.toDomain() = ArtObjectDetails.Maker(
    name, unFixedName, placeOfBirth, dateOfBirth, dateOfBirthPrecision, dateOfDeath,
    dateOfDeathPrecision, placeOfDeath, occupation, roles, nationality, biography,
    productionPlaces, qualification
)

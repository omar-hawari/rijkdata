package com.omarhawari.rijksdata.presentation.art_objects_list

import com.omarhawari.rijksdata.domain.models.ArtObject

sealed class SectionedArtObject {

    class ListItem(val artObject: ArtObject) : SectionedArtObject()
    class ListHeader(val artistName: String) : SectionedArtObject()

}

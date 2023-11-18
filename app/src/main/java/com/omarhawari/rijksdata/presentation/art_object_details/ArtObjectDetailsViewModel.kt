package com.omarhawari.rijksdata.presentation.art_object_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.omarhawari.rijksdata.presentation.PARAM_OBJECT_NUMBER
import javax.inject.Inject

class ArtObjectDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {


    val artObjectId: String

    init {
        artObjectId = savedStateHandle.get<String>(PARAM_OBJECT_NUMBER) ?: ""
    }

}
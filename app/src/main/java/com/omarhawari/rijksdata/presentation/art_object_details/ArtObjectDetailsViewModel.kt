package com.omarhawari.rijksdata.presentation.art_object_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omarhawari.rijksdata.core.DataResult
import com.omarhawari.rijksdata.domain.models.ArtObjectDetails
import com.omarhawari.rijksdata.domain.usecases.GetArtObjectDetailsUseCase
import com.omarhawari.rijksdata.presentation.PARAM_OBJECT_NUMBER
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtObjectDetailsViewModel @Inject constructor(
    private val getArtObjectDetailsUseCase: GetArtObjectDetailsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val viewState = MutableStateFlow<ViewState>(ViewState.Loading)

    private val artObjectNumber: String

    init {
        artObjectNumber = savedStateHandle.get<String>(PARAM_OBJECT_NUMBER) ?: ""
        getObjectDetails()
    }

    fun getObjectDetails() {
        viewState.value = ViewState.Loading
        viewModelScope.launch {
            viewState.value =
                when (val result = getArtObjectDetailsUseCase(objectNumber = artObjectNumber)) {
                    is DataResult.Failure -> ViewState.Error(result.exception)
                    is DataResult.Success -> ViewState.Content(result.response)
                }
        }
    }

    sealed class ViewState {
        data class Content(
            val artObjectDetails: ArtObjectDetails,
        ) : ViewState()

        class Error(val exception: Exception) : ViewState()
        data object Loading : ViewState()
    }


}
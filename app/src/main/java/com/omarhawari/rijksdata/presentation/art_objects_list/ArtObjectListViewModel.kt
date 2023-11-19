package com.omarhawari.rijksdata.presentation.art_objects_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omarhawari.rijksdata.core.DataResult
import com.omarhawari.rijksdata.domain.models.ArtObject
import com.omarhawari.rijksdata.domain.usecases.GetArtObjectListUseCase
import com.omarhawari.rijksdata.domain.usecases.GetArtObjectListUseCase.Companion.SORT_BY_ARTIST
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtObjectListViewModel @Inject constructor(private val getArtObjectListUseCase: GetArtObjectListUseCase) :
    ViewModel() {

    val viewState = MutableStateFlow<ViewState>(ViewState.LoadingFullScreen)

    private val artObjects = arrayListOf<ArtObject>()
    private val sectionedArtObjects = arrayListOf<SectionedArtObject>()

    init {
        getArtObjectList(0)
    }

    fun getArtObjectList(pageIndex: Int, pageSize: Int = 10) {

        if (pageIndex == 0)
            viewState.value = ViewState.LoadingFullScreen
        else
            viewState.value =
                (viewState.value as ViewState.Content).copy(isPaginationLoading = true)

        viewModelScope.launch {
            viewState.value =
                when (val result = getArtObjectListUseCase(pageIndex, pageSize, SORT_BY_ARTIST)) {
                    is DataResult.Failure -> {
                        result.exception.printStackTrace()
                        ViewState.Error(result.exception)
                    }

                    is DataResult.Success -> {
                        addArtObjectsIntoSections(pageIndex, result.response.second)
                        ViewState.Content(
                            pageIndex = result.response.first,
                            list = sectionedArtObjects,
                            isPaginationLoading = false
                        )
                    }
                }
        }
    }

    private fun addArtObjectsIntoSections(pageIndex: Int, addedArtObjects: List<ArtObject>) {
        if (pageIndex == 0) sectionedArtObjects.clear()

        addedArtObjects.forEach { artObject ->
            // If the current item's artist name is different, or this is the first item (which guarantees it's a new artist name), add a new section header.
            if (this.artObjects.isEmpty() || artObject.principalOrFirstMaker != this.artObjects.last().principalOrFirstMaker) {
                sectionedArtObjects.add(SectionedArtObject.ListHeader(artObject.principalOrFirstMaker))
            }
            sectionedArtObjects.add(SectionedArtObject.ListItem(artObject = artObject))
            artObjects.add(artObject)
        }
    }


    sealed class ViewState {
        data class Content(
            val pageIndex: Int,
            val isPaginationLoading: Boolean = false,
            val list: List<SectionedArtObject>
        ) : ViewState()

        class Error(val exception: Exception) : ViewState()
        data object LoadingFullScreen : ViewState()
    }

}
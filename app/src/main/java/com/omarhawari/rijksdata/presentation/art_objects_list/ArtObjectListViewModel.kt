package com.omarhawari.rijksdata.presentation.art_objects_list

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
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

    val actions = MutableStateFlow<Action?>(Action.NoAction)

    val viewState = mutableStateOf<ViewState>(ViewState.Init)

    private val artObjects = arrayListOf<ArtObject>()
    private val sectionedArtObjects = arrayListOf<SectionedArtObject>()

    init {
        getArtObjectList(0)
    }

    fun getArtObjectList(pageIndex: Int, pageSize: Int = 10) {
        viewModelScope.launch {

            if (pageIndex == 0)
                actions.emit(Action.LoadingFullScreen)
            else
                viewState.value =
                    (viewState.value as ViewState.Content).copy(isPaginationLoading = true)

            viewState.value =
                when (val result = getArtObjectListUseCase(pageIndex, pageSize, SORT_BY_ARTIST)) {
                    is DataResult.Failure -> {
                        // If the error happens when the user is refreshing and/or the app just launched for the first time,
                        // the error view will be fullscreen, thus the "ViewState" will be ErrorFullScreen
                        if (pageIndex == 0 && sectionedArtObjects.isEmpty()) {
                            // Clears the progress indicator
                            actions.emit(Action.NoAction)

                            ViewState.ErrorFullScreen(result.exception)
                        }
                        // If the error happens when the user is just scrolling through the list, the user will still be able to view the already
                        // loaded list, but an error SnackBar/Toast should be displayed, which is why the actions flow will emit a new Error object.
                        else {
                            actions.emit(Action.Error(result.exception))

                            ViewState.Content(
                                pageIndex = pageIndex,
                                list = sectionedArtObjects,
                                isPaginationLoading = false
                            )
                        }
                    }

                    is DataResult.Success -> {
                        // Clears the progress indicator
                        actions.emit(Action.NoAction)

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

        class ErrorFullScreen(val exception: Exception) : ViewState()

        data object Init : ViewState()
    }

    sealed class Action {
        class Error(val exception: Exception) : Action()
        data object LoadingFullScreen : Action()
        data object NoAction : Action()
    }

}
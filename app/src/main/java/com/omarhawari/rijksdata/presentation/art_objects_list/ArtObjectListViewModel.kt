package com.omarhawari.rijksdata.presentation.art_objects_list

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omarhawari.rijksdata.core.DataResult
import com.omarhawari.rijksdata.domain.models.ArtObject
import com.omarhawari.rijksdata.domain.usecases.GetArtObjectListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtObjectListViewModel @Inject constructor(private val getArtObjectListUseCase: GetArtObjectListUseCase) :
    ViewModel() {

    val actions = MutableStateFlow<Action?>(Action.NoAction)

    val viewState = mutableStateOf<ViewState>(ViewState.Init)

    val sortBy = mutableStateOf(SortBy.ARTIST)

    // The API can return duplicated results, so putting the ids in a set will help weed out the replicated instances.
    private val artObjectsIds = mutableSetOf<String>()
    private val artObjects = arrayListOf<ArtObject>()
    private val sectionedArtObjects = arrayListOf<SectionedArtObject>()

    init {
        refresh()
    }

    fun refresh() {
        getArtObjectList(0)
    }

    fun paginate(pageIndex: Int) {
        getArtObjectList(pageIndex)
    }

    private fun getArtObjectList(pageIndex: Int, pageSize: Int = 10) {
        viewModelScope.launch {

            if (pageIndex == 0)
                actions.emit(Action.LoadingFullScreen)
            else
                viewState.value =
                    (viewState.value as ViewState.Content).copy(isPaginationLoading = true)

            viewState.value =
                when (val result = getArtObjectListUseCase(pageIndex, pageSize, sortBy.value)) {
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
        if (pageIndex == 0) {
            sectionedArtObjects.clear()
            artObjects.clear()
        }

        addedArtObjects.forEach { artObject ->

            // If the current item's artist name is different, or this is the first item (which guarantees it's a new artist name), add a new section header.
            if (this.artObjects.isEmpty() || artObject.principalOrFirstMaker != this.artObjects.last().principalOrFirstMaker) {

                // Additionally, only add headers if the results are grouped by artists.
                if (listOf(SortBy.ARTIST, SortBy.ARTIST_DESC).contains(sortBy.value))
                    sectionedArtObjects.add(SectionedArtObject.ListHeader(artObject.principalOrFirstMaker))
            }
            if (!artObjectsIds.contains(artObject.id)) {
                sectionedArtObjects.add(SectionedArtObject.ListItem(artObject = artObject))
                artObjects.add(artObject)
                artObjectsIds.add(artObject.id)
            }
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
        // This error is different that ViewState.ErrorFullScreen, as it will only show a Toast
        class Error(val exception: Exception) : Action()
        data object LoadingFullScreen : Action()

        // Blank action to clear whatever came before it.
        data object NoAction : Action()
    }

    enum class SortBy {
        ARTIST,
        ARTIST_DESC,
        RELEVANCE,
        OBJECT_TYPE,
        CHRONOLOGIC,
        ACHRONOLOGIC,
    }

}
package com.omarhawari.rijksdata.presentation.art_objects_list

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

    val viewState = MutableStateFlow<ViewState>(ViewState.LoadingFullScreen)

    private val artObjects = mutableListOf<ArtObject>()

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
            viewState.value = when (val result = getArtObjectListUseCase(pageIndex, pageSize)) {
                is DataResult.Failure -> ViewState.Error(result.exception)
                is DataResult.Success -> {
                    if (pageIndex == 0) artObjects.clear()
                    artObjects.addAll(result.response.second)
                    ViewState.Content(
                        pageIndex = result.response.first,
                        list = artObjects,
                        isPaginationLoading = false
                    )
                }
            }
        }
    }


    sealed class ViewState {
        data class Content(
            val pageIndex: Int,
            val isPaginationLoading: Boolean = false,
            val list: List<ArtObject>
        ) : ViewState()

        class Error(val exception: Exception) : ViewState()
        data object LoadingFullScreen : ViewState()
    }

}
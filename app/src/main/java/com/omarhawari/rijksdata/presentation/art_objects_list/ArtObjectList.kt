package com.omarhawari.rijksdata.presentation.art_objects_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.currentRecomposeScope
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.omarhawari.rijksdata.domain.models.ArtObject
import com.omarhawari.rijksdata.presentation.Screen
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun ArtObjectList(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ArtObjectListViewModel = hiltViewModel()
) {

    val viewState = viewModel.viewState.collectAsState()

    val swipeRefreshState =
        rememberSwipeRefreshState(isRefreshing = viewState.value is ArtObjectListViewModel.ViewState.LoadingFullScreen)

    SwipeRefresh(state = swipeRefreshState, onRefresh = {
        viewModel.getArtObjectList(
            0
        )
    }, modifier = modifier) {

        when (viewState.value) {
            is ArtObjectListViewModel.ViewState.Content -> {

                val content = (viewState.value as ArtObjectListViewModel.ViewState.Content)
                val state = rememberLazyListState()

                LazyColumn(modifier = Modifier, state = state) {
                    items(if (content.isPaginationLoading) content.list.size + 1 else content.list.size) { index ->
                        if (index < content.list.size)
                            ArtObjectListItem(
                                artObject = content.list[index],
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                navController.navigate(
                                    Screen.ArtObjectDetailsScreen.route + "/${
                                        content.list[index].id
                                    }"
                                )
                            }
                        else
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                CircularProgressIndicator()
                            }
                    }
                }
                if (!state.canScrollForward) {
                    viewModel.getArtObjectList(pageIndex = content.pageIndex + 1)
                }
            }

            is ArtObjectListViewModel.ViewState.Error -> {

            }

            is ArtObjectListViewModel.ViewState.LoadingFullScreen -> {
                swipeRefreshState.isRefreshing = true
            }
        }
    }
}

@Composable
fun ArtObjectListItem(modifier: Modifier = Modifier, artObject: ArtObject, onClick: () -> Unit) {

    Column(modifier = modifier.clickable(onClick = onClick)) {
        Text(
            text = artObject.title,
            modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 16.dp, bottom = 8.dp)
        )
        Text(
            text = artObject.principalOrFirstMaker,
            modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 16.dp)
        )
    }

}

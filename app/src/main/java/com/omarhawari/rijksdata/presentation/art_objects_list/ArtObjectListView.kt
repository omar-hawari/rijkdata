package com.omarhawari.rijksdata.presentation.art_objects_list

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.omarhawari.rijksdata.domain.models.ArtObject
import com.omarhawari.rijksdata.presentation.Screen
import com.omarhawari.rijksdata.presentation.components.ErrorComponent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtObjectListView(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ArtObjectListViewModel = hiltViewModel()
) {

    // This state is used to determine if the alert dialog is shown or not.
    val openAlertDialog = remember { mutableStateOf(false) }

    // Used to scroll to the last item of the list.
    val coroutineScope = rememberCoroutineScope()

    val swipeRefreshState = rememberSwipeRefreshState(false)

    val viewState = viewModel.viewState

    val action = viewModel.actions.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.background(Color.Black),
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
                title = {
                    Text(
                        text = "Rijk",
                        style = MaterialTheme.typography.titleLarge.copy(color = Color.White)
                    )
                },
                actions = {
                    IconButton(onClick = {
                        // Setting this state to true shows the dialog.
                        openAlertDialog.value = true
                    }) {
                        Icon(
                            imageVector = Icons.Default.List,
                            contentDescription = "Sort by",
                            tint = Color.White
                        )
                    }
                }
            )
        },

        ) { paddingValues ->
        SwipeRefresh(
            state = swipeRefreshState, onRefresh = {
                viewModel.refresh()
            }, modifier = modifier
                .fillMaxHeight()
                .padding(paddingValues)
        ) {

            when (viewState.value) {
                is ArtObjectListViewModel.ViewState.Content -> {

                    val content = (viewState.value as ArtObjectListViewModel.ViewState.Content)
                    val state = rememberLazyGridState()

                    LazyVerticalGrid(
                        modifier = Modifier,
                        state = state,
                        columns = GridCells.Fixed(2)
                    ) {

                        content.list.forEach {
                            when (it) {
                                is SectionedArtObject.ListHeader -> {
                                    item(span = {
                                        GridItemSpan(2)
                                    }) {
                                        Text(
                                            it.artistName,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .background(
                                                    color = MaterialTheme.colorScheme.primary.copy(
                                                        alpha = 0.8f
                                                    )
                                                )
                                                .padding(vertical = 16.dp),
                                            textAlign = TextAlign.Center,
                                            style = TextStyle(fontSize = 20.sp, color = Color.White)
                                        )
                                    }
                                }

                                is SectionedArtObject.ListItem -> item {
                                    ArtObjectListItem(
                                        artObject = it.artObject,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        navController.navigate(
                                            Screen.ArtObjectDetailsScreen.route + "/${
                                                it.artObject.objectNumber
                                            }"
                                        )
                                    }
                                }
                            }
                        }

                        // If there's pagination loading, add an additional item with a progress indicator.
                        if (content.isPaginationLoading) {
                            item(span = {
                                GridItemSpan(2)
                            }) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp)
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                        }

                        if (!state.canScrollForward) {
                            // In order to show the progress indicator when it's visible the list scrolls to its last item when the user reaches the final item and pagination is started.
                            if (content.isPaginationLoading) {
                                coroutineScope.launch {
                                    state.scrollToItem(content.list.size)
                                }
                            }
                            // Otherwise, initiate pagination with {content.pageIndex + 1}
                            else {
                                viewModel.paginate(pageIndex = content.pageIndex + 1)
                            }

                        }
                    }

                }

                is ArtObjectListViewModel.ViewState.ErrorFullScreen -> {

                    val context = LocalContext.current
                    Toast.makeText(
                        context,
                        (viewState.value as ArtObjectListViewModel.ViewState.ErrorFullScreen).exception.message,
                        Toast.LENGTH_SHORT
                    ).show()

                    ErrorComponent(
                        modifier = Modifier.fillMaxSize(),
                        (viewState.value as ArtObjectListViewModel.ViewState.ErrorFullScreen).exception,
                        onRefresh = {
                            viewModel.refresh()
                        })
                }

                ArtObjectListViewModel.ViewState.Init -> {
                    // Fill the screen in order for the swipe refresh layout to be able to show the progress indicator of the SwipeRefresh component.
                    Box(modifier = Modifier.fillMaxSize())
                }
            }
        }

        if (openAlertDialog.value)
            SortByDialog(
                currentOption = viewModel.sortBy.value,
                onDismiss = {
                    openAlertDialog.value = false
                }, onSelect = {
                    viewModel.sortBy.value = it
                    viewModel.refresh()
                    openAlertDialog.value = false
                })

    }

    when (action.value) {
        is ArtObjectListViewModel.Action.Error -> {
            swipeRefreshState.isRefreshing = false
            val context = LocalContext.current
            Toast.makeText(
                context,
                (action.value as ArtObjectListViewModel.Action.Error).exception.message,
                Toast.LENGTH_SHORT
            ).show()
        }

        ArtObjectListViewModel.Action.LoadingFullScreen -> swipeRefreshState.isRefreshing = true
        else -> swipeRefreshState.isRefreshing = false
    }

}

@Composable
fun ArtObjectListItem(
    modifier: Modifier = Modifier,
    artObject: ArtObject,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .height(200.dp)
            .padding(8.dp)
            .testTag("art_object_item")
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp, pressedElevation = 0.dp),

        ) {
        Box(modifier = Modifier.height(200.dp)) {
            if (artObject.hasImage && artObject.webImage != null) {
                SubcomposeAsyncImage(
                    model = artObject.webImage.url,
                    contentDescription = null,
                    loading = {
                        Box(modifier = Modifier.fillMaxSize()) {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        }
                    },
                    contentScale = ContentScale.Crop,
                )
            }

            Text(
                text = artObject.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Black.copy(alpha = 0.5f),
                                Color.Transparent,
                            ),
                            startY = Float.POSITIVE_INFINITY,
                            endY = 0f
                        )
                    )
                    .padding(
                        start = 8.dp,
                        end = 8.dp,
                        top = 16.dp,
                        bottom = 8.dp
                    )
                    .align(Alignment.BottomCenter),
                style = TextStyle(color = Color.White, lineBreak = LineBreak.Simple),
            )
        }
    }
}

package com.omarhawari.rijksdata.presentation.art_objects_list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.omarhawari.rijksdata.domain.models.ArtObject
import com.omarhawari.rijksdata.presentation.Screen
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ArtObjectList(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ArtObjectListViewModel = hiltViewModel()
) {

    val viewState = viewModel.viewState.collectAsState()

    val coroutineScope = rememberCoroutineScope()


    val swipeRefreshState =
        rememberSwipeRefreshState(isRefreshing = viewState.value is ArtObjectListViewModel.ViewState.LoadingFullScreen)

    SwipeRefresh(state = swipeRefreshState, onRefresh = {
        viewModel.getArtObjectList(
            0
        )
    }, modifier = modifier.fillMaxHeight()) {

        when (viewState.value) {
            is ArtObjectListViewModel.ViewState.Content -> {

                val content = (viewState.value as ArtObjectListViewModel.ViewState.Content)
                val state = rememberLazyGridState()

                LazyVerticalGrid(modifier = Modifier, state = state, columns = GridCells.Fixed(2)) {

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
                                                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                                            )
                                            .padding(vertical = 16.dp),
                                        textAlign = TextAlign.Center,
                                        style = TextStyle(fontSize = 20.sp)
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

                    if (!state.canScrollForward && content.isPaginationLoading) {
                        coroutineScope.launch {
                            state.scrollToItem(content.list.size)
                        }
                    } else if (!state.canScrollForward) {
                        viewModel.getArtObjectList(pageIndex = content.pageIndex + 1)
                    }
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
fun ArtObjectListItem(
    modifier: Modifier = Modifier,
    artObject: ArtObject,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .height(200.dp)
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp, pressedElevation = 0.dp),

        ) {
        Box(modifier = Modifier.height(200.dp)) {
            if (artObject.hasImage && artObject.webImage != null) {
                SubcomposeAsyncImage(
                    modifier = Modifier.clickable(onClick = onClick),
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

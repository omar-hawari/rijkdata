package com.omarhawari.rijksdata.presentation.art_object_details

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.omarhawari.rijksdata.presentation.components.ErrorComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtObjectDetailsView(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: ArtObjectDetailsViewModel = hiltViewModel()
) {

    val title = remember { mutableStateOf("") }

    val viewState = viewModel.viewState

    val swipeRefreshState =
        rememberSwipeRefreshState(isRefreshing = viewState.value is ArtObjectDetailsViewModel.ViewState.Loading)

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                modifier = Modifier.background(Color.Black),
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
                title = {
                    Text(
                        text = title.value,
                        style = MaterialTheme.typography.titleLarge.copy(color = Color.White),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) {
        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = { viewModel.getObjectDetails() },
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),

            ) {
            when (viewState.value) {
                is ArtObjectDetailsViewModel.ViewState.Content -> {

                    val artObjectDetails =
                        (viewState.value as ArtObjectDetailsViewModel.ViewState.Content).artObjectDetails

                    title.value =
                        artObjectDetails.title
                    Column {
                        artObjectDetails.webImage?.let {

                            val scale = remember { mutableStateOf(1f) }

                            val state =
                                rememberTransformableState { scaleChange, _, _ ->
                                    // Scale values are captured here, this could be improved with providing the user the ability to zoom in from a specific offset.
                                    scale.value = (scale.value * scaleChange).coerceIn(1f, 5f)
                                }

                            SubcomposeAsyncImage(
                                model = artObjectDetails.webImage.url,
                                contentDescription = null,
                                loading = {
                                    Box(modifier = Modifier.fillMaxSize()) {
                                        CircularProgressIndicator(
                                            modifier = Modifier.align(
                                                Alignment.Center
                                            )
                                        )
                                    }
                                },
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .height(250.dp)
                                    // Here the scale values are applied, which zooms in the photo.
                                    .graphicsLayer(
                                        scaleX = scale.value,
                                        scaleY = scale.value,
                                    )
                                    .transformable(state = state)
                            )

                            DetailsRow(key = "Artist:", value = artObjectDetails.principalMaker)

                            DetailsRow(
                                key = "Date:",
                                value = artObjectDetails.dating.presentingDate
                            )
                        }
                    }
                }

                is ArtObjectDetailsViewModel.ViewState.Error -> {
                    ErrorComponent(
                        modifier = Modifier.fillMaxSize(),
                        (viewState.value as ArtObjectDetailsViewModel.ViewState.Error).exception,
                        onRefresh = {
                            viewModel.getObjectDetails()
                        })
                }

                ArtObjectDetailsViewModel.ViewState.Loading -> {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }

}

@Composable
fun DetailsRow(modifier: Modifier = Modifier, key: String, value: String) {
    Row(modifier) {

        Text(
            text = key, modifier = Modifier
                .padding(8.dp)
                .weight(1f)
        )

        Text(
            text = value, modifier = Modifier
                .padding(8.dp)
                .weight(1f)
        )

    }
}
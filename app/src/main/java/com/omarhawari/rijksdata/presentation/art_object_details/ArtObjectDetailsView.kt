package com.omarhawari.rijksdata.presentation.art_object_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

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
                    title.value =
                        (viewState.value as ArtObjectDetailsViewModel.ViewState.Content).artObjectDetails.title
                    Text(text = title.value, modifier = Modifier.fillMaxSize())
                }

                is ArtObjectDetailsViewModel.ViewState.Error -> {

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
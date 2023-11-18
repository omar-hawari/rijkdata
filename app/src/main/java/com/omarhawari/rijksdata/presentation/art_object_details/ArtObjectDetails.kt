package com.omarhawari.rijksdata.presentation.art_object_details

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun ArtObjectDetails(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: ArtObjectDetailsViewModel = hiltViewModel()
) {

    Text(text = viewModel.artObjectId)

}
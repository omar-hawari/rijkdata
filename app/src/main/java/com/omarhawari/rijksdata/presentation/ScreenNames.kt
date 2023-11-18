package com.omarhawari.rijksdata.presentation

sealed class Screen(val route: String) {
    data object ArtObjectsListScreen : Screen("art_objects_list_screen")
    data object ArtObjectDetailsScreen : Screen("art_object_details_screen")
}

const val PARAM_OBJECT_NUMBER = "objectNumber"


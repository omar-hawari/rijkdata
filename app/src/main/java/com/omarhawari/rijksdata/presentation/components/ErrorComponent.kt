package com.omarhawari.rijksdata.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun ErrorComponent(modifier: Modifier, exception: Exception, onRefresh: () -> Unit) {

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .clickable(onClick = onRefresh)
                .width(200.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(imageVector = Icons.Default.Refresh, contentDescription = "Refresh Icon")
            Text(text = "Try again")
            // TODO: Come up with more user friendly messages for errors.
            exception.message?.let { Text(text = it, textAlign = TextAlign.Center) }
        }
    }

}
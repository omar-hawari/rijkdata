package com.omarhawari.rijksdata.presentation.art_objects_list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SortByDialog(
    currentOption: ArtObjectListViewModel.SortBy,
    onDismiss: () -> Unit,
    onSelect: (ArtObjectListViewModel.SortBy) -> Unit
) {
    val selectedOption = remember { mutableStateOf(currentOption) }
    val options = ArtObjectListViewModel.SortBy.values()

    AlertDialog(
        confirmButton = {
            TextButton(
                onClick = {
                    onSelect(selectedOption.value)
                }
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Cancel")
            }
        },
        onDismissRequest = { onDismiss() },
        title = {
            Text(text = "Choose an option")
        },
        text = {
            Column {
                options.forEachIndexed { index, option ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = currentOption == option,
                                onClick = {
                                    selectedOption.value = option
                                    onSelect(selectedOption.value)
                                }
                            )
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedOption.value == option,
                            onClick = { selectedOption.value = option }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = when (option) {
                                ArtObjectListViewModel.SortBy.ARTIST -> "Artist"
                                ArtObjectListViewModel.SortBy.ARTIST_DESC -> "Artist (Descending)"
                                ArtObjectListViewModel.SortBy.RELEVANCE -> "Relevance"
                                ArtObjectListViewModel.SortBy.OBJECT_TYPE -> "Object Type"
                                ArtObjectListViewModel.SortBy.CHRONOLOGIC -> "Chronologically"
                                ArtObjectListViewModel.SortBy.ACHRONOLOGIC -> "Achronologically"
                            }
                        )
                    }
                }
            }
        }
    )
}

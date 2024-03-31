package com.se2.fabler.ui.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.se2.fabler.R
import com.se2.fabler.models.TabData
import com.se2.fabler.ui.components.CustomTabStrip
import com.se2.fabler.ui.components.SearchBar

@Composable
fun SearchScreen(onSearchToggle: () -> Unit) {
    Box {
        Box(Modifier.padding(0.dp, 65.dp, 0.dp, 0.dp)) {
            CustomTabStrip(
                listOf(
                    TabData("NOVELS", R.drawable.baseline_menu_book_36) {
                        SearchBook()
                    },
                    TabData("READERS", R.drawable.baseline_diversity_1_36) {
                        SearchUser()
                    },
                )
            )
        }
        SearchBar { onSearchToggle() }
    }
}

@Composable
@Preview(showBackground = true)
private fun PreviewSearchScreen() {
    Column { SearchScreen {} }
}
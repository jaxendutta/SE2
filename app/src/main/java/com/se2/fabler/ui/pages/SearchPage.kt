package com.se2.fabler.ui.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.se2.fabler.AppModel
import com.se2.fabler.R
import com.se2.fabler.getTestAppModel
import com.se2.fabler.models.BookData
import com.se2.fabler.models.TabData
import com.se2.fabler.models.UserData
import com.se2.fabler.ui.components.CustomTabStrip
import com.se2.fabler.ui.components.SearchBar
import com.se2.fabler.ui.views.BookListView
import com.se2.fabler.ui.views.UserListView
import kotlinx.coroutines.flow.flowOf

@Composable
fun SearchPage(app: AppModel) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedTabIndex by remember { mutableIntStateOf(-1) }
    val bookSearchEnabled = selectedTabIndex == 0
    val userSearchEnabled = selectedTabIndex == 1
    val emptyBookFlow = flowOf(PagingData.from(listOf<BookData>())).collectAsLazyPagingItems()
    val emptyUserFlow = flowOf(PagingData.from(listOf<UserData>())).collectAsLazyPagingItems()
    Box {
        Box(Modifier.padding(0.dp, 65.dp, 0.dp, 0.dp)) {
            CustomTabStrip(
                listOf(
                    TabData("NOVELS", R.drawable.baseline_menu_book_36) {
                        BookListView(
                            if (bookSearchEnabled)
                                app.repository.getBookSearchResultStream(searchQuery)
                                    .collectAsLazyPagingItems()
                            else emptyBookFlow
                        ) { book ->
                            // TODO: Implement book details navigation
                        }
                    },
                    TabData("READERS", R.drawable.baseline_diversity_1_36) {
                        UserListView(
                            if (userSearchEnabled)
                                app.repository.getUserSearchResultStream(searchQuery)
                                    .collectAsLazyPagingItems()
                            else emptyUserFlow
                        ) { user ->
                            app.userToDisplayId = user.id
                            app.pushView("ProfilePage")
                        }
                    },
                )
            ) { tab: TabData, index: Int ->
                selectedTabIndex = index
            }
        }
        SearchBar({
            searchQuery = it
            if(selectedTabIndex == -1)
                selectedTabIndex = 0
        }, {
            app.popView()
        })
    }
}

@Composable
@Preview(showBackground = true)
private fun PreviewSearchPage() {
    Column { SearchPage(getTestAppModel()) }
}

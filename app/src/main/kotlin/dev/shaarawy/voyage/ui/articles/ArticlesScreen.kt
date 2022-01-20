package dev.shaarawy.voyage.ui.articles

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import dev.shaarawy.voyage.ui.articles.item.ArticleItem

/**
 * @author Mohamed Elshaarawy on Oct 27, 2021.
 */

@Composable
fun ArticlesScreen() {
    val articlesViewModel: ArticlesViewModel = viewModel()
    val lazyPagingItems = articlesViewModel.articlePagingDataFlow.collectAsLazyPagingItems()
    Scaffold {
        SwipeRefresh(
            state = SwipeRefreshState(lazyPagingItems.loadState.refresh is LoadState.Loading || lazyPagingItems.loadState.append is LoadState.Loading),
            onRefresh = lazyPagingItems::refresh,
        ) {
            LazyColumn(contentPadding = PaddingValues(8.dp)) {
                items(lazyPagingItems) {
                    ArticleItem(articleItemViewModel = it)
                }
            }
        }
    }
}
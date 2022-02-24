package dev.shaarawy.voyage.ui.articles

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.shaarawy.voyage.data.entities.SortingSpecifier
import dev.shaarawy.voyage.domain.ArticlesUseCase
import dev.shaarawy.voyage.ui.articles.item.ArticleItemViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

/**
 * @author Mohamed Elshaarawy on Oct 27, 2021.
 */
@HiltViewModel
class ArticlesViewModel @Inject constructor(
    private val articlesUseCase: ArticlesUseCase
) : ViewModel() {
    private val _sortingSpecifierState = MutableStateFlow<SortingSpecifier?>(null)
    val sortingSpecifierState: StateFlow<SortingSpecifier?> = _sortingSpecifierState
    val articlePagingDataFlow: Flow<PagingData<ArticleItemViewModel>> =
        _sortingSpecifierState.flatMapLatest {
            articlesUseCase.getArticlesPager(it)
        }
}
package dev.shaarawy.voyage.domain

import androidx.paging.PagingData
import androidx.paging.filter
import androidx.paging.map
import dev.shaarawy.voyage.data.entities.SortingSpecifier
import dev.shaarawy.voyage.data.repositories.ArticlesRepository
import dev.shaarawy.voyage.ui.articles.item.ArticleItemViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * @author Mohamed Elshaarawy on Oct 27, 2021.
 */
class ArticlesUseCase @Inject constructor(
    private val articlesRepository: ArticlesRepository
) {

    fun getArticlesPager(sortingSpecifier: SortingSpecifier? = null): Flow<PagingData<ArticleItemViewModel>> {
        return articlesRepository.getArticlesPager(sortingSpecifier).map {
            it.filter { article ->
                article.imageUrl.isNotBlank()
            }.map {article ->
                ArticleItemViewModel(article)
            }
        }
    }

}
package dev.shaarawy.voyage.data.pagingSources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.shaarawy.voyage.data.entities.Article
import dev.shaarawy.voyage.data.repositories.ArticlesRepository

/**
 * @author Mohamed Elshaarawy on Oct 17, 2021.
 */
class ArticlesPagingSource(
    private val articlesRepository: ArticlesRepository
) : PagingSource<Int, Article>() {
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(state.config.pageSize)
                ?: anchorPage?.nextKey?.minus(state.config.pageSize)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val pageSize = params.loadSize
        val skipCount = params.key ?: 0
        return try {
            val articles = articlesRepository.getArticles(pageSize, skipCount)
            LoadResult.Page(
                data = articles,
                prevKey = if (skipCount >= pageSize) skipCount - pageSize else null,
                nextKey = if (articles.size < pageSize) null else skipCount + pageSize
            )
        } catch (t: Throwable) {
            LoadResult.Error(t)
        }
    }
}
package dev.shaarawy.voyage.ui.articles.item

import dev.shaarawy.voyage.data.entities.Article
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import logcat.logcat

/**
 * @author Mohamed Elshaarawy on Oct 27, 2021.
 */
class ArticleItemViewModel(
    val article: Article,
) {
    private val _clickFlow by lazy { MutableSharedFlow<Unit>(extraBufferCapacity = 1) }
    val clickFlow by lazy { _clickFlow.asSharedFlow() }

    fun onItemClick() {
        _clickFlow.tryEmit(Unit)
        logcat("ArticleItemViewModel") { article.title }
    }
}
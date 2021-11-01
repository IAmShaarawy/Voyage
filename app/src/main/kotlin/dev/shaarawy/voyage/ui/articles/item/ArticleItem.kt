package dev.shaarawy.voyage.ui.articles.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import dev.shaarawy.voyage.R

/**
 * @author Mohamed Elshaarawy on Oct 27, 2021.
 */

@Composable
fun ArticleItem(articleItemViewModel: ArticleItemViewModel?) {
    if (articleItemViewModel == null) {
        Text(text = "No Article")
        return
    }
    Card(modifier = Modifier.padding(8.dp)) {
        Image(
            painter = rememberImagePainter(articleItemViewModel.article.imageUrl, builder = {
                this.placeholder(R.drawable.voyage_bg1)
            }),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .height(128.dp)
        )
    }
}
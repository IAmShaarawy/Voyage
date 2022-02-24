package dev.shaarawy.voyage.ui.articles.item

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import dev.shaarawy.voyage.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

/**
 * @author Mohamed Elshaarawy on Oct 27, 2021.
 */

@Composable
fun ArticleItem(articleItemViewModel: ArticleItemViewModel?) {
    if (articleItemViewModel == null) {
        Text(text = "No Article")
        return
    }

    val context = LocalContext.current

    LaunchedEffect(key1 = Unit){
        articleItemViewModel.clickFlow.collectLatest {
            delay(1000)
                Toast.makeText(context, "item", Toast.LENGTH_LONG).show()
            }
    }
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable(onClick = articleItemViewModel::onItemClick)
    ) {
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
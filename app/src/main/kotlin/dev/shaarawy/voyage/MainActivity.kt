package dev.shaarawy.voyage

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dagger.hilt.android.AndroidEntryPoint
import dev.shaarawy.voyage.ui.articles.ArticlesScreen
import dev.shaarawy.voyage.ui.theme.VoyageTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SplashInstaller(this) {
            setContent {
                VoyageTheme {
                    Surface(color = MaterialTheme.colors.background) {
                        ArticlesScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun Logo() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.voyage_logo_trans),
            contentDescription = "logo",
            modifier = Modifier.size(128.dp)
        )
        Text(
            text = "Voyage",
            style = MaterialTheme.typography.h3,
            color = MaterialTheme.colors.onPrimary
        )
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    VoyageTheme {
        Logo()
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DarkPreview() {
    VoyageTheme {
        Logo()
    }
}
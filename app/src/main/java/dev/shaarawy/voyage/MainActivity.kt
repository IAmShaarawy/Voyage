package dev.shaarawy.voyage

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import dev.shaarawy.voyage.ui.theme.VoyageTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import logcat.LogPriority
import logcat.logcat

class MainActivity : ComponentActivity(), CoroutineScope by MainScope() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logcat(LogPriority.ERROR) { "First log" }
        setContent {
            VoyageTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Logo()
                }
            }
        }
    }
}

@Composable
fun Logo() {
    Column(modifier = Modifier.fillMaxHeight(),verticalArrangement = Arrangement.Center) {
        Image(painter = painterResource(id = R.drawable.voyage_logo_trans),
            contentDescription = "logo",
            modifier = Modifier.fillMaxWidth())
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
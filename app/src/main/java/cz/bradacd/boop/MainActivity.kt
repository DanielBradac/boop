package cz.bradacd.boop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cz.bradacd.boop.ui.screens.HomeScreen
import cz.bradacd.boop.ui.theme.BoopTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BoopTheme {
                App()
            }
        }
    }
}

@Composable
fun App() {
    Scaffold { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            HomeScreen()
        }
    }
}



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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import cz.bradacd.boop.ui.navigation.Screens
import cz.bradacd.boop.ui.screens.BoopScreen
import cz.bradacd.boop.ui.screens.HomeScreen
import cz.bradacd.boop.ui.theme.BoopTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BoopTheme {
                Scaffold { paddingValues ->
                    Box(modifier = Modifier.padding(paddingValues)) {
                        App()
                    }
                }
            }
        }
    }
}

@Composable
fun App() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screens.HomeScreen.url) {
        composable(Screens.HomeScreen.url) { HomeScreen(navController = navController) }
        composable(
            Screens.BoopScreen.url,
            arguments = listOf(navArgument("boopName") { type = NavType.StringType })
        ) { backStackEntry ->
            BoopScreen(
                navController = navController,
                boopName = backStackEntry.arguments?.getString("boopName") ?: ""
            )
        }
    }

}



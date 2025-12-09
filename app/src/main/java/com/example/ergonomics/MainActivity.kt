package com.example.ergonomics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ergonomics.ui.components.TopBar
import com.example.ergonomics.ui.screens.ExportScreen
import com.example.ergonomics.ui.screens.HomeScreen
import com.example.ergonomics.ui.screens.MeasurementScreen
import com.example.ergonomics.ui.theme.ErgonomicsTheme
import com.example.ergonomics.ui.viewmodel.ErgonomicsVM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ErgonomicsTheme {
                val vm = hiltViewModel<ErgonomicsVM>()
                val navController = rememberNavController()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { TopBar(navController = navController) }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "HomeScreen",
                        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(700)) },
                        exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(700)) },
                        popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(700)) },
                        popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(700)) }
                    ) {
                        composable(route = "HomeScreen") {
                            HomeScreen(
                                vm = vm,
                                navController = navController,
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                        composable("MeasurementScreen") {
                            MeasurementScreen(
                                vm = vm,
                                navController = navController,
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                        composable("ExportScreen") {
                            ExportScreen(
                                vm = vm,
                                navController = navController,
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ErgonomicsTheme {
        Greeting("Android")
    }
}
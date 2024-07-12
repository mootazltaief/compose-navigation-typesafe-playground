package com.ltaief.navigationtypesafe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import androidx.navigation.toRoute
import com.ltaief.navigationtypesafe.ui.theme.NavigationComposeTypeSafeTheme
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavigationComposeTypeSafeTheme {
                window.navigationBarColor = colorScheme.background.toArgb()
                val navController: NavHostController = rememberNavController()
                MyBottomBar(navController)
            }
        }
    }
}

@Serializable
sealed class TopLevelDestination {
    @Serializable
    data object Home : TopLevelDestination()

    @Serializable
    data class Profile(val name: String) : TopLevelDestination()

    @Serializable
    data object Search : TopLevelDestination()
}

fun NavHostController.navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {

    val topLevelNavOptions = navOptions {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        // Avoid multiple copies of the same destination when
        // reselecting the same item
        launchSingleTop = true
        // Restore state when reselecting a previously selected item
        restoreState = true
    }
    navigate(topLevelDestination, topLevelNavOptions)
}

@Composable
fun MyBottomBar(navController: NavHostController) {
    Scaffold(
        bottomBar = {
            BottomAppBar(containerColor = Color.Transparent) {
                IconButton(
                    onClick = {
                        navController.navigateToTopLevelDestination(TopLevelDestination.Home)
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Filled.Home, contentDescription = "Home")
                }

                IconButton(
                    onClick = {
                        navController.navigateToTopLevelDestination(TopLevelDestination.Search)
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Filled.Search, contentDescription = "Search")
                }

                IconButton(
                    onClick = {
                        navController.navigateToTopLevelDestination(TopLevelDestination.Profile("John Doe"))
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Filled.Face, contentDescription = "Profile")
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = TopLevelDestination.Home,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<TopLevelDestination.Home> {
                HomeScreen()
            }
            composable<TopLevelDestination.Search> {
                SearchScreen()
            }
            composable<TopLevelDestination.Profile> {
                val profile = it.toRoute<TopLevelDestination.Profile>()
                ProfileScreen(profile.name)
            }
        }
    }
}

@Composable
fun HomeScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Home", textAlign = TextAlign.Center)
        }
    }
}

@Composable
fun SearchScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Search", textAlign = TextAlign.Center)
        }
    }
}

@Composable
fun ProfileScreen(name: String) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = name, textAlign = TextAlign.Center)
        }
    }
}

package com.martin.myapplication.presentation.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.martin.myapplication.R
import com.martin.myapplication.presentation.ui.theme.BackgroundColor
import com.martin.myapplication.presentation.view.BottomNavItem.Companion.items
import com.martin.myapplication.presentation.view.detailsscreen.MovieDetailsPage
import com.martin.myapplication.presentation.view.homescreen.HomeScreenPage
import com.martin.myapplication.presentation.view.searchscreen.SearchScreen
import com.martin.myapplication.presentation.view.searchscreen.WatchListScreen

sealed class BottomNavItem(val route: String, val iconid: Int, val label: String) {
    object Home : BottomNavItem("home", R.drawable.home, "Home")
    object Search : BottomNavItem("search", R.drawable.search, "Search")
    object WatchList : BottomNavItem("watchlist", R.drawable.watchlist, "Watch List")
    companion object {
        val items = listOf(Home, Search, WatchList)
    }
}

@Composable
fun MainScreen() {
    val navController: NavHostController = rememberNavController()
    Scaffold(
        bottomBar = {
            Box(
                modifier = Modifier
                    .background(color = Color(0xFF0296E5))
                    .height(80.dp)
            ) {
                BottomAppBar(
                    backgroundColor = BackgroundColor,
                    modifier = Modifier
                        .padding(top = 2.dp)
                        .height(78.dp)
                ) {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentRoute = navBackStackEntry?.destination?.route

                    items.forEach { item ->
                        NavigationBarItem(
                            selected = currentRoute == item.route,
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = {
                                Icon(
                                    painter = painterResource(id = item.iconid),
                                    contentDescription = stringResource(id = item.iconid),
                                )
                            },
                            label = { Text(text = item.label, color = if (currentRoute == item.route) Color(0xFF0296E5) else Color(0xFF67686D)) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color(0xFF0296E5),
                                unselectedIconColor = Color(0xFF67686D),
                                indicatorColor = Color.Transparent
                            ),
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Home.route,
            Modifier.padding(innerPadding)
        ) {
            composable(BottomNavItem.Home.route) {
                HomeScreenPage(goToDetails = { movieId ->
                    navController.navigate("details/$movieId")
                })
            }
            composable(BottomNavItem.Search.route) { SearchScreen(goBack = { navController.navigateUp() }) }
            composable(BottomNavItem.WatchList.route) { WatchListScreen(goBack = { navController.navigateUp() }) }
            composable(
                "details/{movieId}",
                arguments = listOf(navArgument("movieId") { type = NavType.IntType })
            ) { backStackEntry ->
                val movieId = backStackEntry.arguments?.getInt("movieId") ?: 0
                Log.d("NavHost", "movieId: $movieId")
                MovieDetailsPage(goBack = { navController.navigateUp() }, movieId)
            }
        }
    }
}
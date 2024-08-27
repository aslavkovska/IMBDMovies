package com.martin.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.martin.myapplication.presentation.ui.theme.MyApplicationTheme
import com.martin.myapplication.presentation.view.detailsscreen.DetailsScreen
import com.martin.myapplication.presentation.view.homescreen.BottomNav
import com.martin.myapplication.presentation.view.homescreen.HomeScreenPage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    bottomBar = { BottomNav() }
                ) {
                    HomeScreenPage()
//                    DetailsScreen(movieId = 533535)
//                    val movieId : Int = 533535
//                    navController.navigate("details_screen?movieId=$movieId")
                }
            }
        }
    }
}





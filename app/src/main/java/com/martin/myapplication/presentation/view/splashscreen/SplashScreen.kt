package com.martin.myapplication.presentation.view.splashscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.martin.myapplication.R
import com.martin.myapplication.presentation.ui.theme.BackgroundColor

@Composable
fun SplashScreen(){
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = BackgroundColor
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.popcorn),
                contentDescription = null,
                modifier = Modifier
                    .size(189.dp)
            )
        }
    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    SplashScreen()
}
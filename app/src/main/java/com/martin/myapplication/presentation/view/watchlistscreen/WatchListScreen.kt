package com.martin.myapplication.presentation.view.searchscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.martin.myapplication.R
import com.martin.myapplication.presentation.ui.theme.BackgroundColor

@Composable
fun WatchListScreen() {
//    var selectedIndex by remember { mutableStateOf(1) }
//    val navController = rememberNavController()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
    ){
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                TopWatchListBar()

                if(movieList.isEmpty()){
                    EmptyWatchList()
                }
                else{
                    MovieGrid(movieList)
                }
            }
        }
    }
}

@Composable
fun TopWatchListBar() {
    Box(
        modifier = Modifier
            .padding(24.dp)
            .fillMaxWidth()
            .heightIn(max = 36.dp)
            .background(BackgroundColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = { },
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_left),
                    contentDescription = "Left Icon",
                    modifier = Modifier.size(AssistChipDefaults.IconSize),
                    tint = Color.White
                )
            }

            Text(
                text = "Watch List",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.align(Alignment.CenterVertically)
            )

            IconButton(
                onClick = { },
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.info_circle),
                    contentDescription = "Right Icon",
                    modifier = Modifier.size(AssistChipDefaults.IconSize),
                    tint = Color.Transparent
                )
            }
        }
    }
}

@Composable
fun EmptyWatchList(){
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
                painter = painterResource(id = R.drawable.box),
                contentDescription = null,
                modifier = Modifier
                    .size(76.dp)
                    .padding(bottom = 20.dp),
            )
            Text(
                text = "There Is No Movie Yet!",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .width(252.dp)
            )
            Text(
                text = "Find your movie by Type title, categories, years, etc",
                color = Color(146, 146, 157, 255),
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.width(252.dp)
            )
        }
    }
}

@Preview
@Composable
fun EmptyWatchListPreview() {
    EmptyWatchList()
}

@Preview
@Composable
fun WatchListScreenPreview(){
    val navController = rememberNavController()
    WatchListScreen()
}

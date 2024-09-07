package com.martin.myapplication.presentation.view.homescreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.martin.myapplication.R
import com.martin.myapplication.presentation.ui.theme.BackgroundColor
import com.martin.myapplication.presentation.view.searchscreen.SearchScreen
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import com.martin.myapplication.presentation.view.searchscreen.WatchListScreen
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.martin.myapplication.BuildConfig
import com.martin.myapplication.data.remote.model.NowPlayingMovies
import com.martin.myapplication.data.remote.model.PopularMovies
import com.martin.myapplication.data.remote.model.Result
import com.martin.myapplication.data.remote.model.TopRatedMovies
import com.martin.myapplication.data.remote.model.UpcomingMovies
import com.martin.myapplication.presentation.view.detailsscreen.DetailsScreen
import com.martin.myapplication.presentation.view.detailsscreen.MovieDetailsPage
import com.martin.myapplication.presentation.view.splashscreen.SplashScreen
import com.martin.myapplication.presentation.viewmodel.HomeViewModel
import com.slack.eithernet.ApiResult

//@Composable
//fun HomeScreen() {
//    HomeScreenPage()
//}

@Composable
fun HomeScreenPage(goToDetails: (Int) -> Unit) {
    val homeViewModel = hiltViewModel<HomeViewModel>()
    val stateTopRated by homeViewModel.stateTopRated.collectAsState()
    val stateUpcoming by homeViewModel.stateUpcoming.collectAsState()
    val stateNowPlaying by homeViewModel.stateNowPlaying.collectAsState()
    val statePopular by homeViewModel.statePopular.collectAsState()
    val isLoading by homeViewModel.isLoading.collectAsState()

    var selectedIndex by remember { mutableStateOf(0) }

    val currentState = when (selectedIndex) {
        0 -> stateTopRated
        1 -> stateUpcoming
        2 -> stateNowPlaying
        3 -> statePopular
        else -> stateTopRated
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            if (isLoading) {
//                CircularProgressIndicator(
//                    modifier = Modifier
//                        .padding(16.dp)
//                )
                SplashScreen()
            }
            when (val resultTopRated = stateTopRated) {
                is ApiResult.Success -> {
                    val topRatedMovies = resultTopRated.value.results

                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CenteredText()
                        Spacer(modifier = Modifier.height(16.dp))

                        MoviesRow(movies = topRatedMovies){ movieId ->
                            goToDetails(movieId)
                        }

                        Spacer(modifier = Modifier.height(30.dp))

                        NavigationBar(selectedIndex = selectedIndex,
                            onSelectedIndexChanged = { index -> selectedIndex = index })

                        when (currentState) {
                            is ApiResult.Success -> {
                                val response = currentState.value
                                val movies = when (response) {
                                    is TopRatedMovies -> response.results
                                    is PopularMovies -> response.results
                                    is UpcomingMovies -> response.results
                                    is NowPlayingMovies -> response.results
                                    else -> emptyList()
                                }
                                    MoviesGrid(movies = movies){ movieId ->
                                        goToDetails(movieId)
                                    }
                            }
                            is ApiResult.Failure -> {
                                Text(
                                    text = "Error: ${currentState}",
                                    color = Color.Red,
                                    modifier = Modifier
                                        .padding(16.dp)
                                )
                            }
                            null -> {
                                Text(
                                    text = "No data available",
                                    modifier = Modifier
                                        .padding(16.dp)
                                )
                            }
                        }
                    }
                }
                is ApiResult.Failure -> {
                    Text(
                        text = "Error: ${resultTopRated}",
                        color = Color.Red,
                        modifier = Modifier
                            .padding(16.dp)
                    )
                }
                null -> {
                    Text(
                        text = "No data for Top Rated Movies available",
                        modifier = Modifier
                            .padding(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun CenteredText() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        contentAlignment = Alignment.TopStart
    ) {
        Text(
            text = "What do you want to watch?",
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun MoviePosterImage(
    posterPath: String,
    modifier: Modifier = Modifier,
) {
    val imageBaseUrl = BuildConfig.IMAGE_BASE_URL
    val imageUrl = "$imageBaseUrl$posterPath"

    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        contentDescription = null,
        contentScale = ContentScale.FillBounds,
        modifier = modifier,
//        contentScale = ContentScale.Crop
    )
}

//@Composable
//fun MovieCardTop(posterPath: String,
//    number: String,
//    movieId: Int,
//    onClick: (Int) -> Unit,) {
//    Box(
//        modifier = Modifier
//            .width(164.61.dp)
//            .height(250.dp)
//    ) {
//        Box(
//            modifier = Modifier
//                .align(Alignment.TopEnd)
//                .width(144.61.dp)
//                .height(210.dp)
//                .clickable { onClick(movieId) }
//        ){
//            MoviePosterImage(
//                posterPath = posterPath,
//                modifier = Modifier
//                    .width(144.61.dp)
//                    .height(210.dp)
//                    .clip(RoundedCornerShape(10.dp))
//            )
//        }
//        Box(
//            modifier = Modifier
//                .width(164.61.dp)
//                .height(250.dp)
//                .align(Alignment.BottomStart)
//                .background(Color.Transparent)
//        ) {
//            Text(
//                text = number,
//                color = Color(2, 150, 229, 255),
//                style = TextStyle(
//                    fontSize = 104.sp,
//                    fontWeight = FontWeight.Bold
//                ),
//                modifier = Modifier
//                    .align(Alignment.BottomStart)
//            )
//            Text(
//                text = number,
//                color = Color(36, 42, 50, 255),
//                style = TextStyle(
//                    fontSize = 96.sp,
//                    fontWeight = FontWeight.Bold
//                ),
//            modifier = Modifier
//                .offset(x = 2.5.dp, y = -4.dp)
//                .align(Alignment.BottomStart))
//        }
//    }
//}

//@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
//@Composable
//fun MovieCardTopPreview() {
//    MovieCardTop(
//        drawable = R.drawable.ic_launcher_foreground,
//        number = "1"
//    )
//}

@Composable
fun MoviesRow(
    movies: List<Result>,
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit,
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(30.18.dp),
        contentPadding = PaddingValues(horizontal = 34.06.dp),
        modifier = modifier.background(color = Color(0x242A32))
    ) {
        items(movies) { movie ->
            Box(
                modifier = Modifier
                    .width(164.61.dp)
                    .height(250.dp)
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .width(144.61.dp)
                        .height(210.dp)
                        .clickable { onClick(movie.id) }
                ){
                    MoviePosterImage(
                        posterPath = movie.posterPath,
                        modifier = Modifier
                            .width(144.61.dp)
                            .height(210.dp)
                            .clip(RoundedCornerShape(10.dp))
                    )
                }
                Box(
                    modifier = Modifier
                        .width(164.61.dp)
                        .height(250.dp)
                        .align(Alignment.BottomStart)
                        .background(Color.Transparent)
                ) {
                    Text(
                        text = "${movies.indexOf(movie)+1}",
                        color = Color(2, 150, 229, 255),
                        style = TextStyle(
                            fontSize = 104.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                    )
                    Text(
                        text = "${movies.indexOf(movie)+1}",
                        color = Color(36, 42, 50, 255),
                        style = TextStyle(
                            fontSize = 96.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier
                            .offset(x = 2.5.dp, y = -4.dp)
                            .align(Alignment.BottomStart))
                }
            }
        }
    }
}

//@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
//@Composable
//fun MoviesRowPreview() {
//    MoviesRow(moviesList)
//}

@Composable
fun NavigationBar(selectedIndex: Int,
                  onSelectedIndexChanged: (Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val tabs = listOf("Top Rated", "Upcoming", "Now playing", "Popular")

        tabs.forEachIndexed { index, tab ->
            NavBarItem(
                label = tab,
                isSelected = selectedIndex == index,
                onClick = { onSelectedIndexChanged(index) }
            )
        }
    }
    Spacer(modifier = Modifier.height(10.dp))
}

@Composable
fun NavBarItem(label: String, isSelected: Boolean, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Text(
            text = label,
            color = Color.White,
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )
        if (isSelected) {
            Box(
                modifier = Modifier
                    .width(80.dp)
                    .height(4.dp)
                    .background(Color(0xFF67686D))
            )
        }
    }
}

//@Composable
//fun MovieCardBottom(
//    posterPath: String,
//    movieId: Int,
//    modifier: Modifier = Modifier,
//    onClick: (Int) -> Unit,
//) {
////    val navController = rememberNavController()
//    Surface(
//        shape = MaterialTheme.shapes.medium,
//        modifier = modifier
//            .background(color = BackgroundColor)
//            .clickable {
//                onClick(movieId)
////                navController.navigate("details/$movieId")
//            }
//    ) {
//        Row(
//            horizontalArrangement = Arrangement.Center,
//            modifier = Modifier
//                .background(color = BackgroundColor)
//                .width(100.dp)
//        ) {
//            MoviePosterImage(
//                posterPath = posterPath,
//                modifier = Modifier
//                    .width(100.dp)
//                    .height(146.dp)
//                    .clip(RoundedCornerShape(10.dp))
//            )
//        }
//    }
//}


//@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
//@Composable
//fun MovieCardBottomPreview() {
//    MovieCardBottom(
//        drawable = R.drawable.movie,
//    )
//}


@Composable
fun MoviesGrid(
    movies: List<Result>,
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(horizontal = 22.dp, vertical = 30.dp),
                horizontalArrangement = Arrangement.spacedBy(13.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp),
                modifier = Modifier
                    .background(color = Color(0x242A32))
                    .height(500.dp)
                    .fillMaxWidth()
            ) {
                items(movies) { movie ->
                    Surface(
                        shape = MaterialTheme.shapes.medium,
                        modifier = Modifier
                            .background(color = BackgroundColor)
                            .clickable { onClick(movie.id) }
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .background(color = BackgroundColor)
                                .width(100.dp)
                        ) {
                            MoviePosterImage(
                                posterPath = movie.posterPath,
                                modifier = Modifier
                                    .width(100.dp)
                                    .height(146.dp)
                                    .clip(RoundedCornerShape(10.dp))
                            )
                        }
                    }
                }
            }
        }
    }
}









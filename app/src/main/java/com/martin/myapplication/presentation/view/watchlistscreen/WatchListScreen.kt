package com.martin.myapplication.presentation.view.searchscreen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.martin.myapplication.R
import com.martin.myapplication.domain.model.WatchListMoviesModel
import com.martin.myapplication.presentation.state.MovieDetailsUiState
import com.martin.myapplication.presentation.ui.theme.BackgroundColor
import com.martin.myapplication.presentation.view.homescreen.MoviePosterImage
import com.martin.myapplication.presentation.viewmodel.MovieDetailsViewModel
import com.martin.myapplication.presentation.viewmodel.WatchListMoviesViewModel

@Composable
fun WatchListPage(goBack: () -> Unit) {
    val watchListViewModel: WatchListMoviesViewModel = hiltViewModel()
    val watchListState = watchListViewModel.state.collectAsState()
    val watchListMovies = watchListState.value.movies

    LaunchedEffect(21456817) {
        watchListViewModel.fetchMoviesFromWatchList(21456817)
    }

    WatchListScreen(goBack, watchListMovies)
}

@Composable
fun WatchListScreen(goBack: () -> Unit,
                    watchListMovies: List<WatchListMoviesModel.Result>) {
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

                TopWatchListBar(goBack)

                if(watchListMovies.isEmpty()){
                    EmptyWatchList()
                }
                else{
                    MovieGrid(watchListMovies)
                }
            }
        }
    }
}

@Composable
fun TopWatchListBar(goBack: () -> Unit) {
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
                onClick = { goBack() },
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
fun MovieCard(movie: WatchListMoviesModel.Result) {
//    val movieDetailsViewModel: MovieDetailsViewModel = hiltViewModel()
//    movieDetailsViewModel.fetchMovieDetails(movie.id)
//    val state = movieDetailsViewModel.state.collectAsState()

    val movieDetailsViewModel: MovieDetailsViewModel = hiltViewModel()
    movieDetailsViewModel.fetchMovieDetails(movie.id)
    val state = movieDetailsViewModel.state.collectAsState()
    movie.runtime = state.value.movieDetails?.runtime ?: 0
    movie.genres = state.value.movieDetails?.genres ?: emptyList()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
            .width(307.dp)
            .height(120.dp),
        colors = CardDefaults.cardColors(containerColor = BackgroundColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            MoviePosterImage(
                posterPath = movie.posterPath,
                modifier = Modifier
                    .width(95.dp)
                    .height(120.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp)
            ) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 16.sp,
                    color = Color.White,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(bottom = 15.dp)
                )
                Row() {
                    Icon(
                        painter = painterResource(id = R.drawable.grade),
                        contentDescription = null,
                        modifier = Modifier.size(AssistChipDefaults.IconSize),
                        tint = Color(0xFFFF8700)
                    )
                    Text(
                        text = " ${movie.voteAverage.toString()}",
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 12.sp,
                        color = Color(0xFFFF8700),
                        textAlign = TextAlign.Start,
                    )
                }
                Row() {
                    Icon(
                        painter = painterResource(id = R.drawable.genre),
                        contentDescription = null,
                        modifier = Modifier.size(AssistChipDefaults.IconSize),
                        tint = Color.White
                    )
//                    var genres: String = ""
//                    state.value.movieDetails?.genres?.get(movie.genreIds.get(1))?.name?.let {
//                        genres += "${it}, "
//                    }
                    Text(
//                        text = genres,
//                        text = " ${movie.genreIds?.joinToString(separator = ", ")  ?: ""  }",
                        text = movie.genres?.joinToString(separator = ", ") { it.name }  ?: "",
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 12.sp,
                        color = Color.White,
                        textAlign = TextAlign.Start,
                    )
                }
                Row() {
                    Icon(
                        painter = painterResource(id = R.drawable.year),
                        contentDescription = null,
                        modifier = Modifier.size(AssistChipDefaults.IconSize),
                        tint = Color.White
                    )
                    Text(
                        text = " ${movie.releaseDate?.take(4) ?: ""}",
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 12.sp,
                        color = Color.White,
                        textAlign = TextAlign.Start,
                    )
                }
                Row() {
                    Icon(
                        painter = painterResource(id = R.drawable.duration),
                        contentDescription = null,
                        modifier = Modifier.size(AssistChipDefaults.IconSize),
                        tint = Color.White
                    )
                    Text(
//                        text = " 100 minutes",
                        text = " ${movie.runtime} minutes",
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 12.sp,
                        color = Color.White,
                        textAlign = TextAlign.Start,
                    )
                }
            }
        }
    }
}

@Composable
fun MovieGrid(movies: List<WatchListMoviesModel.Result>) {
    Box() {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            items(movies) { movie ->
                MovieCard(movie)
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

//@Preview
//@Composable
//fun WatchListScreenPreview(){
//    val navController = rememberNavController()
//    WatchListScreen()
//}

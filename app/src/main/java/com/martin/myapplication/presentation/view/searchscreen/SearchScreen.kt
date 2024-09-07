package com.martin.myapplication.presentation.view.searchscreen

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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.martin.myapplication.R
import com.martin.myapplication.presentation.ui.theme.BackgroundColor
import com.martin.myapplication.presentation.viewmodel.SearchViewModel
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.martin.myapplication.domain.model.SearchMovieModel
import com.martin.myapplication.presentation.state.SearchMoviesUiState
import com.martin.myapplication.presentation.view.homescreen.MoviePosterImage
import com.martin.myapplication.presentation.viewmodel.MovieDetailsViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class Movietmp(
    val title: String,
    val grade: Float,
    val genre: String,
    val duration: String,
    val year: String,
    val imageid: Int,
)

//val movieList: List<Movie> = listOf()

val movieList = listOf(
    Movietmp(
        title = "Movie One",
        grade = 8.5f,
        genre = "Action",
        duration = "120 minutes",
        year = "2024",
        imageid = R.drawable.movie2
    ),
    Movietmp(
        title = "Movie Two",
        grade = 9.5f,
        genre = "Drama",
        duration = "140 minutes",
        year = "2023",
        imageid = R.drawable.movie2
    ),
    Movietmp(
        title = "Movie Three",
        grade = 8.0f,
        genre = "Sci-Fi",
        duration = "150 minutes",
        year = "2022",
        imageid = R.drawable.movie2
    ),
    Movietmp(
        title = "Movie Four",
        grade = 6.5f,
        genre = "Comedy",
        duration = "110 minutes",
        year = "2021",
        imageid = R.drawable.movie2
    ),
    Movietmp(
        title = "Movie Five",
        grade = 6.0f,
        genre = "Comedy",
        duration = "110 minutes",
        year = "2021",
        imageid = R.drawable.movie2
    ),
    Movietmp(
        title = "Movie Six",
        grade = 7.0f,
        genre = "Comedy",
        duration = "110 minutes",
        year = "2021",
        imageid = R.drawable.movie2
    )
)

@Composable
fun SearchScreen(goBack: () -> Unit) {

    val searchViewModel: SearchViewModel = hiltViewModel()
    val searchState = searchViewModel.state.collectAsState()
    val searchInput = searchViewModel.searchText.collectAsState()
    val isSearching = searchViewModel.isSearching.collectAsState()

//    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
//    val hasResults by remember {
//        mutableStateOf(true)
//    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
//            .nestedScroll(scrollBehavior.nestedScrollConnection),
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

                TopSearchBar(goBack)

                SearchBar(searchInput, searchViewModel,searchState)

                if (isSearching.value && searchInput.value != "") {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 30.dp),
                        color = Color(0xFF0296E5)
                    )
                } else if (searchState.value.movies.isEmpty() && searchInput.value != "" && !isSearching.value) {
                    EmptySearch()
                } else {
                    MovieGrid(searchState.value.movies)
                }

//                MovieGrid(movies = movieList)

//                if(movieList.isEmpty()){
//                    EmptySearch()
//                }
//                else{
//
//                }
            }
        }
    }
}

@Composable
fun TopSearchBar(goBack: () -> Unit) {
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
                onClick = { goBack()},
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_left),
                    contentDescription = "Left Icon",
                    modifier = Modifier.size(AssistChipDefaults.IconSize),
                    tint = Color.White
                )
            }

            Text(
                text = "Search",
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
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
fun SearchBar(
    searchInput: State<String>,
    searchViewModel: SearchViewModel,
    searchState: State<SearchMoviesUiState>,
    modifier: Modifier = Modifier
) {

//    val coroutineScope = rememberCoroutineScope()
//    var debounceJob: Job? by remember { mutableStateOf(null) }

    TextField(
        value = searchInput.value,
        trailingIcon = {
            IconButton(onClick = {  searchViewModel.getMovieResults(searchInput.value) }) {
                Icon(
                    painter = painterResource(id = R.drawable.search2),
                    contentDescription = null,
                    modifier = Modifier.size(AssistChipDefaults.IconSize),
                    tint = Color(0xFF67686D)
                )
            }
        },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color(0xFF3A3F47),
            focusedContainerColor = Color(0xFF3A3F47),
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            cursorColor = Color.Gray
        ),
        placeholder = {
            Text(
                text = "Search",
                fontSize = 14.sp,
                color = Color(0xFF67686D)
            )
        },
        onValueChange = {
            searchViewModel.onSearchTextChange(it)
            if (it.isEmpty()) {
                searchViewModel.onEmptyInput()
            }
//            else{
//                searchViewModel.getMovieResults(it)
//            }
        },
//        onValueChange = { newValue ->
//            searchViewModel.onSearchTextChange(newValue)
//
//            debounceJob?.cancel()
//            debounceJob = coroutineScope.launch {
//                if (newValue.isNotEmpty()) {
//                    delay(300)
//                    searchViewModel.getMovieResults(newValue)
//                } else {
//                    searchViewModel.onEmptyInput()
//                }
//            }
//        },
        singleLine = true,
        shape = MaterialTheme.shapes.medium.copy(all = CornerSize(16.dp)),
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 36.dp)
            .padding(horizontal = 24.dp),
    )
}

//@Preview
//@Composable
//fun SearchBarPreview() {
////    val navController = rememberNavController()
//    SearchScreen()
//}

@Composable
fun MovieCard(movie: SearchMovieModel.Result) {
    val movieDetailsViewModel: MovieDetailsViewModel = hiltViewModel()
    val state = movieDetailsViewModel.state.collectAsState()

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
                    text = movie.originalTitle,
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
                    var genres: String = ""
                    state.value.movieDetails?.genres?.get(movie.genreIds.get(1))?.name?.let {
                        genres += "${it}, "
                    }
                    Text(
                        text = genres,
//                        text = " ${movie.genreIds?.joinToString(separator = ", ")  ?: ""  }",
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
                        text = " 100 minutes",
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
fun MovieGrid(movies: List<SearchMovieModel.Result>) {
    Box() {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            itemsIndexed(movies) { id, movie ->
                MovieCard(movie)
            }
        }
    }
}

@Composable
fun EmptySearch(){
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
            Icon(
                painter = painterResource(id = R.drawable.search3),
                contentDescription = null,
                modifier = Modifier
                    .size(76.dp)
                    .padding(bottom = 20.dp),
                tint = Color(0xFFFF8700)
            )
            Text(
                text = "We Are Sorry, We Can Not Find The Movie :(",
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

//@Preview
//@Composable
//fun PreviewErrorScreen() {
//    EmptySearch()
//}
//
//@Preview
//@Composable
//fun PreviewMovieCard() {
//    MovieCard(movieList.get(0))
//}
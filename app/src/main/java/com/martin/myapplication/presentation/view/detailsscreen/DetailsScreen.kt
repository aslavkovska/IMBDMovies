package com.martin.myapplication.presentation.view.detailsscreen

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.martin.myapplication.R
import com.martin.myapplication.presentation.state.DetailsUiState
import com.martin.myapplication.presentation.ui.theme.BackgroundColor
import com.martin.myapplication.presentation.view.homescreen.MoviePosterImage
import com.martin.myapplication.presentation.viewmodel.MovieDetailsViewModel
import androidx.compose.runtime.State
import com.martin.myapplication.data.remote.model.MovieReviews.AuthorDetails

data class Review(
    val imageId: Int,
    val name: String,
    val description: String,
    val grade: Float
)

data class Cast(
    val imageId: Int,
    val name: String
)

val reviewsList = listOf(
    Review(
        imageId = R.drawable.reviewperson,
        name = "Alice Smith",
        description = "From DC Comics comes the Suicide Squad, an antihero team of incarcerated supervillains who act as deniable assets for the United States government.",
        grade = 8.5f
    ),
    Review(
        imageId = R.drawable.reviewperson,
        name = "Bob Johnson",
        description = "From DC Comics comes the Suicide Squad, an antihero team of incarcerated supervillains who act as deniable assets for the United States government.",
        grade = 7.5f
    ),
    Review(
        imageId = R.drawable.reviewperson,
        name = "Charlie Brown",
        description = "From DC Comics comes the Suicide Squad, an antihero team of incarcerated supervillains who act as deniable assets for the United States government.",
        grade = 9.0f
    )
)

val castList = listOf(
    Cast(imageId = R.drawable.castperson, name = "Actor One"),
    Cast(imageId = R.drawable.castperson, name = "Actor Two"),
    Cast(imageId = R.drawable.castperson, name = "Actor Three"),
    Cast(imageId = R.drawable.castperson, name = "Actor Four"),
    Cast(imageId = R.drawable.castperson, name = "Actor Five"),
    Cast(imageId = R.drawable.castperson, name = "Actor Six"),
    Cast(imageId = R.drawable.castperson, name = "Actor Seven"),
)

@Composable
fun MovieDetailsPage(goBack: () -> Unit, id: Int) {
    val movieDetailsViewModel: MovieDetailsViewModel = hiltViewModel()
    val movieDetailsState = movieDetailsViewModel.state.collectAsState()

    LaunchedEffect(id) {
        movieDetailsViewModel.fetchMovieDetails(id)
    }

    DetailsScreen(goBack, movieDetailsState)
}

@Composable
fun DetailsScreen(goBack: () -> Unit, state: State<DetailsUiState>){
    var selectedIndex by remember { mutableStateOf(0) }

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
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                TopDetailsBar(goBack)

                TopMovieCard(state)

                DetailsLine(state)

                DetailsNavBar(selectedIndex = selectedIndex,
                    onSelectedIndexChanged = { index -> selectedIndex = index })

                if(selectedIndex == 0)
                    AboutMovie(state)
                else if(selectedIndex == 1)
                    ReviewsGrid(state)
                else if(selectedIndex == 2)
                    CastGrid(castList)
                else
                    AboutMovie(state)
            }
        }
    }
}

//@Preview
//@Composable
//fun DetailsScreenPreview(){
//    DetailsScreen()
//}

@Composable
fun TopDetailsBar(goBack: () -> Unit) {
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
                text = "Detail",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.align(Alignment.CenterVertically)
            )

            IconButton(
                onClick = { },
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.save),
                    contentDescription = "Right Icon",
                    modifier = Modifier.size(AssistChipDefaults.IconSize),
                    tint = Color.White
                )
            }
        }
    }
}

//@Preview
//@Composable
//fun TopBarPreview() {
//    TopDetailsBar()
//}

@Composable
fun TopMovieCard(state: State<DetailsUiState>){
    Box(modifier = Modifier
        .height(271.dp)
        .fillMaxWidth()
    ){
        Box(modifier = Modifier
            .height(211.dp)
            .fillMaxWidth()
        ){
            MoviePosterImage(
                posterPath = state.value.movieDetails?.backdropPath.toString() ?: state.value.movieDetails?.posterPath.toString(),
                modifier = Modifier
                    .height(250.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
            )
            Box(modifier = Modifier
                .width(54.dp)
                .height(24.dp)
                .align(Alignment.BottomEnd)
                .padding(end = 8.dp, bottom = 8.dp)
                .background(Color(0x51572836), shape = RoundedCornerShape(16.dp))
            ){
                Row() {
                    Icon(
                        painter = painterResource(id = R.drawable.grade),
                        contentDescription = null,
                        modifier = Modifier
                            .size(AssistChipDefaults.IconSize)
                            .padding(start = 4.dp),
                        tint = Color(0xFFFF8700)
                    )
                    Text(
                        text = "${state.value.movieDetails?.voteAverage}",
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 12.sp,
                        color = Color(0xFFFF8700),
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
        Box(modifier = Modifier
            .align(Alignment.BottomStart)
            .padding(start = 29.dp)){
            MoviePosterImage(
                posterPath = state.value.movieDetails?.posterPath.toString(),
                modifier = Modifier
                    .width(95.dp)
                    .height(120.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
        }
        Box(modifier = Modifier
            .align(Alignment.BottomStart)
            .padding(start = 130.dp, bottom = 25.dp)){
            Text(
                text = state.value.movieDetails?.title.toString(),
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 18.sp,
                color = Color.White,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

//@Preview
//@Composable
//    fun TopMovieCardPreview() {
//    TopMovieCard(movieList.get(0))
//}

@Composable
fun DetailsLine(state: State<DetailsUiState>) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            DetailsLineItem(
                iconRes = R.drawable.year,
                text = state.value.movieDetails?.releaseDate?.take(4) ?: ""
            )
            Text(
                text = "|",
                color = Color(0xFF92929D),
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 12.sp
            )
            DetailsLineItem(
                iconRes = R.drawable.duration,
                text = " ${state.value.movieDetails?.runtime} minutes"
            )
            Text(
                text = "|",
                color = Color(0xFF92929D),
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 12.sp
            )
            DetailsLineItem(
                iconRes = R.drawable.genre,
                text = state.value.movieDetails?.genres?.joinToString(separator = ", ") { it.name }  ?: ""            )
        }
    }
}

@Composable
fun DetailsLineItem(iconRes: Int, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier.size(AssistChipDefaults.IconSize),
            tint = Color(0xFF92929D)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            fontSize = 12.sp,
            color = Color(0xFF92929D),
            textAlign = TextAlign.Start,
        )
    }
}

//@Preview
//@Composable
//fun DetailsLinePreview() {
//    DetailsLine(movieList.get(0))
//}

@Composable
fun DetailsNavBar(selectedIndex: Int,
                  onSelectedIndexChanged: (Int) -> Unit){

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val tabs = listOf("About Movie", "Reviews", "Cast")

        tabs.forEachIndexed { index, tab ->
            NavBarItem(
                label = tab,
                isSelected = selectedIndex == index,
                onClick = { onSelectedIndexChanged(index) }
            )
        }
    }
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
//            Spacer(modifier = Modifier.height(7.dp))
            Box(
                modifier = Modifier
                    .width(80.dp)
                    .height(4.dp)
                    .background(Color(0xFF67686D))
            )
        }
    }
}

//@Preview
//@Composable
//fun DetailsBarPreview() {
//    DetailsBar()
//}

@Composable
fun AboutMovie(state: State<DetailsUiState>){
    Box(modifier = Modifier
        .width(317.dp)
        .padding(top = 18.dp, bottom = 10.dp)){
        Text(
            text = "${state.value.movieDetails?.overview.toString()}",
            color = Color.White
        )
    }
}

//@Preview
//@Composable
//fun AboutMoviePreview() {
//    AboutMovie()
//}

//@Composable
//fun ReviewItem(
//    review: Review,
//    modifier: Modifier = Modifier
//) {
//    Surface(
//        shape = MaterialTheme.shapes.medium,
//        modifier = modifier.background(color = BackgroundColor)
//    ) {
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            modifier = Modifier
//                .background(color = BackgroundColor)
//                .height(120.dp)
//                .width(371.dp)
//        ) {
//            Box(modifier = Modifier.width(44.dp)){
//                Image(
//                    painter = painterResource(review.imageId),
//                    contentDescription = null,
//                    contentScale = ContentScale.FillBounds,
//                    modifier = Modifier
//                        .width(44.dp)
//                        .height(44.dp)
//                        .clip(CircleShape)
//                )
//                Box(modifier = Modifier
//                    .align(alignment = Alignment.BottomCenter)
//                    .padding(top = 44.dp)){
//                    Text(
//                        text = "${review.grade}",
//                        fontSize = 12.sp,
//                        color = Color(0xFF0296E5),
//                        textAlign = TextAlign.Center,
//                        fontWeight = FontWeight.Bold
//                    )
//                }
//            }
//            Box(modifier = Modifier.padding(start = 10.dp)){
//                Box{
//                    Text(
//                        text = "${review.name}",
//                        fontSize = 12.sp,
//                        fontWeight = FontWeight.Bold,
//                        color = Color.White,
//                        modifier = Modifier.padding(bottom = 10.dp)
//                    )
//                }
//                Box (){
//                    Text(
//                        text = "${review.description}",
//                        fontSize = 12.sp,
//                        color = Color.White,
//                    )
//                }
//            }
//        }
//    }
//}

@Composable
fun ReviewItem(
    authorDetails: AuthorDetails,
    content: String,
    author: String,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier.background(color = BackgroundColor)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(color = BackgroundColor)
                .padding(8.dp)
        ) {
            Box(modifier = Modifier
                .width(44.dp)
                .align(alignment = Alignment.Top)) {
                if(authorDetails.avatarPath != null){
                    MoviePosterImage(
                        posterPath = authorDetails.avatarPath.toString(),
                        modifier = Modifier
                            .width(44.dp)
                            .height(44.dp)
                            .clip(CircleShape)
                    )
                }
                else{
                    Image(
                    painter = painterResource(R.drawable.reviewperson),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .width(44.dp)
                        .height(44.dp)
                        .clip(CircleShape)
                    )
                }
                Box(
                    modifier = Modifier
                        .align(alignment = Alignment.BottomCenter)
                        .padding(top = 44.dp)
                ) {
                    if(authorDetails.rating != null){
                        Text(
                            text = authorDetails.rating.toString()?: "",
                            fontSize = 12.sp,
                            color = Color(0xFF0296E5),
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
                Spacer(modifier = Modifier.width(8.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 4.dp)
                ) {
                    Text(
                        text = author,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = content,
                        fontSize = 12.sp,
                        color = Color.White,
                    )
                }
            }
    }
}



//@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
//@Composable
//fun ReviewItemPreview() {
//    ReviewItem(
//        review = reviewsList.get(0),
//    )
//}

@Composable
fun ReviewsGrid(
    state: State<DetailsUiState>,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column (
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(1),
                contentPadding = PaddingValues(horizontal = 30.dp),
                horizontalArrangement = Arrangement.spacedBy(13.dp),
                modifier = Modifier
                    .background(color = Color(0x242A32))
                    .height(650.dp)
                    .padding(bottom = 10.dp)
            ) {

                state.value.movieReviews?.let {
                    items(it.results) { item ->
                        ReviewItem(authorDetails = item.authorDetails, content = item.content, author = item.author?:"")
                    }
                }
//                items(state.value.movieReviews.results) { item ->
//                    ReviewItem(authorDetails = item.authorDetails, content = item.content)
//                }
            }
        }
    }
}

//@Preview
//@Composable
//fun ReviewsGridPreview() {
//    ReviewsGrid()
//}

@Composable
fun CastItem(cast: Cast) {
    Box(
        modifier = Modifier
            .width(120.dp)
            .height(125.dp)
            .background(color = BackgroundColor)
//            .padding(4.dp) // Optional padding inside the box
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = cast.imageId),
                contentDescription = "Cast Image",
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = cast.name,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Color.White
            )
        }
    }
}


@Composable
fun CastGrid(castItems: List<Cast>) {
    Box(modifier = Modifier){
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(castItems) { cast ->
                CastItem(cast = cast)
            }
        }
    }

}
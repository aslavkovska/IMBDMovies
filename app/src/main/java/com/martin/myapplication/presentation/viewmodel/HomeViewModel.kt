package com.martin.myapplication.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martin.myapplication.data.remote.model.ErrorResponse
import com.martin.myapplication.data.remote.model.NowPlayingMovies
import com.martin.myapplication.data.remote.model.PopularMovies
import com.martin.myapplication.data.remote.model.TopRatedMovies
import com.martin.myapplication.data.remote.model.UpcomingMovies
import com.martin.myapplication.data.remote.repository.MoviesRepository
import com.slack.eithernet.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

//data class Movie(
//    val title: String,
//    val grade: Float,
//    val genre: String,
//    val duration: String,
//    val year: String,
//    val imageid: Int,
//    val posterid: Int
//)
//
//val moviesList = listOf(
//    Movietmp(
//        title = "Movie One",
//        grade = 8.5f,
//        genre = "Action",
//        duration = "120 minutes",
//        year = "2024",
//        imageid = R.drawable.movie2
//    ),
//    Movietmp(
//        title = "Movie Two",
//        grade = 9.5f,
//        genre = "Drama",
//        duration = "140 minutes",
//        year = "2023",
//        imageid = R.drawable.movie
//    ),
//    Movietmp(
//        title = "Movie Three",
//        grade = 8.0f,
//        genre = "Sci-Fi",
//        duration = "150 minutes",
//        year = "2022",
//        imageid = R.drawable.movie2
//    ),
//    Movietmp(
//        title = "Movie Four",
//        grade = 6.5f,
//        genre = "Comedy",
//        duration = "110 minutes",
//        year = "2021",
//        imageid = R.drawable.movie
//    ),
//    Movietmp(
//        title = "Movie Five",
//        grade = 6.0f,
//        genre = "Comedy",
//        duration = "110 minutes",
//        year = "2021",
//        imageid = R.drawable.movie2
//    ),
//    Movietmp(
//        title = "Movie Six",
//        grade = 7.0f,
//        genre = "Comedy",
//        duration = "110 minutes",
//        year = "2021",
//        imageid = R.drawable.movie
//    ),
//    Movietmp(
//        title = "Movie Seven",
//        grade = 7.0f,
//        genre = "Comedy",
//        duration = "110 minutes",
//        year = "2021",
//        imageid = R.drawable.movie
//    ),
//    Movietmp(
//        title = "Movie Eight",
//        grade = 7.0f,
//        genre = "Comedy",
//        duration = "110 minutes",
//        year = "2021",
//        imageid = R.drawable.movie2
//    )
//)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    private val _stateTopRated = MutableStateFlow<ApiResult<TopRatedMovies, ErrorResponse>?>(null)
    val stateTopRated: StateFlow<ApiResult<TopRatedMovies, ErrorResponse>?> get() = _stateTopRated

    private val _stateNowPlaying = MutableStateFlow<ApiResult<NowPlayingMovies, ErrorResponse>?>(null)
    val stateNowPlaying: StateFlow<ApiResult<NowPlayingMovies, ErrorResponse>?> get() = _stateNowPlaying

    private val _stateUpcoming = MutableStateFlow<ApiResult<UpcomingMovies, ErrorResponse>?>(null)
    val stateUpcoming: StateFlow<ApiResult<UpcomingMovies, ErrorResponse>?> get() = _stateUpcoming

    private val _statePopular = MutableStateFlow<ApiResult<PopularMovies, ErrorResponse>?>(null)
    val statePopular: StateFlow<ApiResult<PopularMovies, ErrorResponse>?> get() = _statePopular

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    init {
        fetchTopRatedMovies()
        fetchNowPlayingMovies()
        fetchUpcomingMovies()
        fetchPopularMovies()
    }

    private fun fetchTopRatedMovies() {
        viewModelScope.launch {
            _isLoading.value = true
            val topRatedMovies = moviesRepository.getTopRatedMovies()
            _stateTopRated.value = topRatedMovies
            _isLoading.value = false
        }
    }

    private fun fetchNowPlayingMovies() {
        viewModelScope.launch {
            _isLoading.value = true
            val nowPlayingMovies = moviesRepository.getNowPlayingMovies()
            _stateNowPlaying.value = nowPlayingMovies
            _isLoading.value = false
        }
    }

    private fun fetchUpcomingMovies() {
        viewModelScope.launch {
            _isLoading.value = true
            val upcomingMovies = moviesRepository.getUpcomingMovies()
            _stateUpcoming.value = upcomingMovies
            _isLoading.value = false
        }
    }

    private fun fetchPopularMovies() {
        viewModelScope.launch {
            _isLoading.value = true
            val popularMovies = moviesRepository.getPopularMovies()
            _statePopular.value = popularMovies
            _isLoading.value = false
        }
    }
}

//class HomeViewModel @Inject constructor(
//    private val moviesRepository: MoviesRepository
//) : ViewModel() {
//
//    private val _topRatedMovies = MutableLiveData<ApiResult<TopRatedMovies, ErrorResultResponse>>()
//    val topRatedMovies: LiveData<ApiResult<TopRatedMovies, ErrorResultResponse>> get() = _topRatedMovies
//
//    init {
//        fetchTopRatedMovies()
//    }
//
//    private fun fetchTopRatedMovies() {
//        viewModelScope.launch {
//            when (val result = moviesRepository.getTopRatedMovies()) {
//                is ApiResult.Success -> {
//                    _topRatedMovies.postValue(result)
//                }
//                is ApiResult.Failure -> {
//                    _topRatedMovies.postValue(result)
//                }
//            }
//        }
//    }
//}


//class HomeViewModel @Inject constructor(
//    private val moviesRepository: MoviesRepository
//): ViewModel() {
//    private val _topRatedMovies = MutableLiveData<ApiResult<TopRatedMovies, ErrorResultResponse>>()
//    val topRatedMovies: LiveData<ApiResult<TopRatedMovies, ErrorResultResponse>> = _topRatedMovies
//
//    init {
//        viewModelScope.launch {
//            _topRatedMovies.value = try {
//                getTopRatedMovies()
//            } catch (e: Exception) {
//                ApiResult.apiFailure(ErrorResultResponse(e.message ?: "Unknown error"))
//            }
//        }
//    }
//
//    private suspend fun getTopRatedMovies(): ApiResult<TopRatedMovies, ErrorResultResponse> {
//        return moviesRepository.getTopRatedMovies()
//    }
//}

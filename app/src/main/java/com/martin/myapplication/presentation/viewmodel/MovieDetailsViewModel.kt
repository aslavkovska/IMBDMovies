package com.martin.myapplication.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martin.myapplication.data.remote.model.ErrorResponse
import com.martin.myapplication.data.remote.model.MovieDetails
import com.martin.myapplication.data.remote.repository.MovieDetailsRepository
import com.martin.myapplication.domain.usecase.MovieDetailsUseCase
import com.martin.myapplication.domain.usecase.MovieReviewsUseCase
import com.martin.myapplication.presentation.state.DetailsUiState
import com.slack.eithernet.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetails: MovieDetailsUseCase,
    private val getMovieReviews: MovieReviewsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(DetailsUiState())
    val state: StateFlow<DetailsUiState> = _state

    fun fetchMovieDetails(id: Int) {
        viewModelScope.launch {
            _state.update { uiState ->
                uiState.copy(isLoading = true)
            }

            getMovieDetails(id).collect { result ->
                _state.update { uiState ->
                    when(result){
                        is ApiResult.Success -> uiState.copy(
                            movieDetails = result.value,
                            error = "",
                        )
                        is ApiResult.Failure -> uiState.copy(
                            movieDetails = null,
                            error = ""
                        )
                    }
                }
            }

            getMovieReviews(id).collect { result ->
                _state.update { uiState ->
                    when(result){
                        is ApiResult.Success -> uiState.copy(
                            movieReviews = result.value,
                            error = "",
                        )
                        is ApiResult.Failure -> uiState.copy(
                            movieReviews = null,
                            error = ""
                        )
                    }
                }
            }
            _state.update { uiState ->
                uiState.copy(isLoading = false)
            }
        }
    }
}

//
//@HiltViewModel
//class MovieDetailsViewModel @Inject constructor(
//    private val movieDetailsRepository: MovieDetailsRepository,
////    private val savedStateHandle: SavedStateHandle,
//) : ViewModel() {
//
//    private val _stateMovie = MutableStateFlow<ApiResult<MovieDetails, ErrorResponse>?>(null)
//    val stateMovie: StateFlow<ApiResult<MovieDetails, ErrorResponse>?> get() = _stateMovie
//
//    private val _isLoading = MutableStateFlow(false)
//    val isLoading: StateFlow<Boolean> get() = _isLoading
//
////    private val movieId: Int = savedStateHandle["movieId"] ?: throw IllegalArgumentException("movieId not found")
//
////    init {
////        Log.d("DetailsViewModel", "Movie ID: $movieId")
////        fetchMovieDetails()
////    }
//
//    fun fetchMovieDetails(id: Int) {
//        viewModelScope.launch {
//            _isLoading.value = true
//            val movieDetails = movieDetailsRepository.getMovieDetails(movieId = id)
//            _stateMovie.value = movieDetails
//            _isLoading.value = false
//        }
//    }
//}
package com.martin.myapplication.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martin.myapplication.data.remote.model.ErrorResponse
import com.martin.myapplication.data.remote.model.MovieDetails
import com.martin.myapplication.data.remote.repository.MovieDetailsRepository
import com.slack.eithernet.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val movieDetailsRepository: MovieDetailsRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _stateMovie = MutableStateFlow<ApiResult<MovieDetails, ErrorResponse>?>(null)
    val stateMovie: StateFlow<ApiResult<MovieDetails, ErrorResponse>?> get() = _stateMovie

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val movieId: Int = savedStateHandle["movieId"] ?: throw IllegalArgumentException("movieId not found")

    init {
        Log.d("DetailsViewModel", "Movie ID: $movieId")
        fetchMovieDetails()
    }

    private fun fetchMovieDetails() {
        viewModelScope.launch {
            _isLoading.value = true
            val movieDetails = movieDetailsRepository.getMovieDetails(movieId = movieId)
            _stateMovie.value = movieDetails
            _isLoading.value = false
        }
    }
}
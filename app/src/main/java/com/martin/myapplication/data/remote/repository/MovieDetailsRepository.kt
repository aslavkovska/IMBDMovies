package com.martin.myapplication.data.remote.repository

import com.martin.myapplication.data.remote.api.MoviesApi
import com.martin.myapplication.data.remote.model.ErrorResponse
import com.martin.myapplication.data.remote.model.MovieDetails
import com.martin.myapplication.data.remote.model.MovieReviews
import com.slack.eithernet.ApiResult
import javax.inject.Inject

class MovieDetailsRepository @Inject constructor(private val moviesApi: MoviesApi) {
    suspend fun getMovieDetails(movieId: Int): ApiResult<MovieDetails, ErrorResponse> {
        val result = moviesApi.getMovieDetails(movieId)

        when (result) {
            is ApiResult.Success -> {
                val movieDetails = result.value
                println("Fetched Movie Details: $movieDetails for movieId: ${movieId}")
            }

            is ApiResult.Failure -> {
                println("Error fetching movie with id ${movieId}")
            }
        }
        return result
    }

    suspend fun getMovieReviews(movieId: Int): ApiResult<MovieReviews, ErrorResponse> {
        val result = moviesApi.getMovieReviews(movieId)

        when (result) {
            is ApiResult.Success -> {
                val movieReviews = result.value
                println("Fetched Movie Reviews: $movieReviews for movieId: ${movieId}")
            }

            is ApiResult.Failure -> {
                println("Error fetching movie with id ${movieId}")
            }
        }
        return result
    }
}
package com.martin.myapplication.data.remote.repository

import com.martin.myapplication.data.remote.api.MoviesApi
import com.martin.myapplication.data.remote.model.ErrorResponse
import com.martin.myapplication.data.remote.model.MovieDetails
import com.slack.eithernet.ApiResult
import retrofit2.http.Path
import javax.inject.Inject

class MovieDetailsRepository @Inject constructor(private val moviesApi: MoviesApi) {
    suspend fun getMovieDetails(movieId: Int): ApiResult<MovieDetails, ErrorResponse> {
        val result = moviesApi.getMovieDetails(movieId = movieId)

        when (result) {
            is ApiResult.Success -> {
                val movie = result.value.title
                println("Fetched Top Rated Movies: $movie")
            }

            is ApiResult.Failure -> {
                println("Error fetching movie with id ${movieId}")
            }
        }
        return result
    }
}
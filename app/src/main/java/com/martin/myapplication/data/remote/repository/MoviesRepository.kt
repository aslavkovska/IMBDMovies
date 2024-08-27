package com.martin.myapplication.data.remote.repository

import com.martin.myapplication.data.remote.api.MoviesApi
import com.martin.myapplication.data.remote.model.ErrorResponse
import com.martin.myapplication.data.remote.model.NowPlayingMovies
import com.martin.myapplication.data.remote.model.PopularMovies
import com.martin.myapplication.data.remote.model.TopRatedMovies
import com.martin.myapplication.data.remote.model.UpcomingMovies
import com.slack.eithernet.ApiResult
import javax.inject.Inject

class MoviesRepository  @Inject constructor(private val moviesApi: MoviesApi) {
    suspend fun getTopRatedMovies(): ApiResult<TopRatedMovies, ErrorResponse> {
        val result = moviesApi.getTopRatedMovies()

        when (result) {
            is ApiResult.Success -> {
                val movies = result.value.results
                println("Fetched Top Rated Movies: $movies")
            }

            is ApiResult.Failure -> {
                println("Error fetching movies")
            }
        }
        return result
    }

    suspend fun getNowPlayingMovies(): ApiResult<NowPlayingMovies, ErrorResponse> {
        val result = moviesApi.getNowPlayingMovies()

        when (result) {
            is ApiResult.Success -> {
                val movies = result.value.results
                println("Fetched Now Playing Movies: $movies")
            }

            is ApiResult.Failure -> {
                println("Error fetching movies")
            }
        }
        return result
    }

    suspend fun getUpcomingMovies(): ApiResult<UpcomingMovies, ErrorResponse> {
        val result = moviesApi.getUpcomingMovies()

        when (result) {
            is ApiResult.Success -> {
                val movies = result.value.results
                println("Fetched Upcoming Movies: $movies")
            }

            is ApiResult.Failure -> {
                println("Error fetching movies")
            }
        }
        return result
    }

    suspend fun getPopularMovies(): ApiResult<PopularMovies, ErrorResponse> {
        val result = moviesApi.getPopularMovies()

        when (result) {
            is ApiResult.Success -> {
                val movies = result.value.results
                println("Fetched Popular Movies: $movies")
            }

            is ApiResult.Failure -> {
                println("Error fetching movies")
            }
        }
        return result
    }
}

//class MoviesRepository @Inject constructor(private val moviesApi: MoviesApi) {
//
//    suspend fun getTopRatedMovies(): ApiResult<TopRatedMovies, ErrorResultResponse> {
//        val result = moviesApi.getTopRatedMovies(language = "en-US", page = 1)
//
//        when (result) {
//            is ApiResult.Success -> {
//                // Log the success response
//                val movies = result.value.results
//                Log.d("MoviesRepository", "Fetched Movies: $movies")
//            }
//
//            is ApiResult.Failure -> {
//                // Log the failure error
//                when (result) {
//                    is ApiResult.Failure.NetworkFailure -> Log.e("MoviesRepository", "Network Failure")
//                    is ApiResult.Failure.HttpFailure -> Log.e("MoviesRepository", "HTTP Failure")
//                    is ApiResult.Failure.ApiFailure -> Log.e("MoviesRepository", "API Failure")
//                    is ApiResult.Failure.UnknownFailure -> Log.e("MoviesRepository", "Unknown Failure")
//                }
//            }
//        }
//
//        return result
//    }
//}

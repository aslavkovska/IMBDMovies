package com.martin.myapplication.data.remote.repository

import com.martin.myapplication.data.remote.api.MoviesApi
import com.martin.myapplication.data.remote.api.WatchlistRequest
import com.martin.myapplication.data.remote.model.ErrorResponse
import com.martin.myapplication.data.remote.model.NowPlayingMovies
import com.martin.myapplication.data.remote.model.PopularMovies
import com.martin.myapplication.data.remote.model.SearchMovie
import com.martin.myapplication.data.remote.model.TopRatedMovies
import com.martin.myapplication.data.remote.model.UpcomingMovies
import com.martin.myapplication.data.remote.model.WatchListMovies
import com.martin.myapplication.data.remote.model.WatchlistResponse
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

    suspend fun getSearchedMovies(input: String): ApiResult<SearchMovie, ErrorResponse> {
        val result = moviesApi.getSearchedMovies(input)

        when (result) {
            is ApiResult.Success -> {
                val movies = result.value.results
                println("Fetched Movies from search $input: $movies")
            }

            is ApiResult.Failure -> {
                println("Error fetching movie")
            }
        }
        return result
    }

    suspend fun addMovieToWatchlist(
        id: Int,
        watchlistRequest: WatchlistRequest,
    ): ApiResult<WatchlistResponse, ErrorResponse> {
        val result = moviesApi.addMovieToWatchList(id, watchlistRequest)

        when (result) {
            is ApiResult.Success -> {
                println("Movie added to watchlist successfully: ${result.value}")
            }

            is ApiResult.Failure -> {
                println("Error adding movie to watchlist")
            }
        }
        return result
    }

    suspend fun getWatchListMovies(
        id: Int,
    ): ApiResult<WatchListMovies, ErrorResponse> {
        val result = moviesApi.getWatchListMovies(id)

        when (result) {
            is ApiResult.Success -> {
                println("Watch List Movies: ${result.value}")
            }

            is ApiResult.Failure -> {
                println("Error adding movie to watchlist")
            }
        }
        return result
    }
}


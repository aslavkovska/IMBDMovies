package com.martin.myapplication.data.remote.api


import com.martin.myapplication.data.remote.model.ErrorResponse
import com.martin.myapplication.data.remote.model.MovieDetails
import com.martin.myapplication.data.remote.model.MovieReviews
import com.martin.myapplication.data.remote.model.NowPlayingMovies
import com.martin.myapplication.data.remote.model.PopularMovies
import com.martin.myapplication.data.remote.model.SearchMovie
import com.martin.myapplication.data.remote.model.TopRatedMovies
import com.martin.myapplication.data.remote.model.UpcomingMovies
import com.martin.myapplication.data.remote.model.WatchListMovies
import com.martin.myapplication.data.remote.model.WatchlistResponse
import com.slack.eithernet.ApiResult
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

data class WatchlistRequest(
    val media_type: String,
    val media_id: Int,
    val watchlist: Boolean
)

interface MoviesApi {
    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): ApiResult<TopRatedMovies, ErrorResponse>

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): ApiResult<NowPlayingMovies, ErrorResponse>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): ApiResult<UpcomingMovies, ErrorResponse>

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): ApiResult<PopularMovies, ErrorResponse>

    @GET("movie/{movieId}")
    suspend fun getMovieDetails(
        @Path("movieId") movieId: Int,
        @Query("language") language: String = "en-US",
    ): ApiResult<MovieDetails, ErrorResponse>

    @GET("movie/{movie_id}/reviews")
    suspend fun getMovieReviews(
        @Path("movie_id") id: Int,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
    ): ApiResult<MovieReviews, ErrorResponse>

    @GET("search/movie")
    suspend fun getSearchedMovies(
        @Query("query") input: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
    ): ApiResult<SearchMovie, ErrorResponse>

    @POST("account/{account_id}/watchlist")
    suspend fun addMovieToWatchList(
        @Path("account_id") id: Int = 21456830,
        @Body watchlistRequest: WatchlistRequest
    ): ApiResult<WatchlistResponse, ErrorResponse>

    @GET("account/{account_id}/watchlist/movies")
    suspend fun getWatchListMovies(
        @Path("account_id") id: Int = 21456830,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
    ): ApiResult<WatchListMovies, ErrorResponse>
}
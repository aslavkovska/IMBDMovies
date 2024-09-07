package com.martin.myapplication.domain.mapper

import com.martin.myapplication.data.remote.model.ErrorResponse
import com.martin.myapplication.data.remote.model.WatchListMovies
import com.martin.myapplication.domain.model.WatchListMoviesModel
import com.slack.eithernet.ApiResult
import javax.inject.Inject

class WatchListMoviesMapper @Inject constructor() {

    fun mapAsResult(response: ApiResult<WatchListMovies, ErrorResponse>): ApiResult<WatchListMoviesModel, ErrorResponse> {
        return when (response) {
            is ApiResult.Success -> {
                val movieModel = response.value.toMovieModel()
                ApiResult.success(movieModel)
            }
            is ApiResult.Failure -> when (response) {
                is ApiResult.Failure.NetworkFailure -> error("")
                is ApiResult.Failure.HttpFailure -> error("")
                is ApiResult.Failure.ApiFailure -> error("")
                is ApiResult.Failure.UnknownFailure -> error("")
            }
        }
    }
}

fun WatchListMovies.toMovieModel(): WatchListMoviesModel {
    return WatchListMoviesModel(
        results = this.results.map { result ->
            WatchListMoviesModel.Result(
                posterPath = result.posterPath,
                releaseDate = result.releaseDate,
                genreIds = result.genreIds,
                title = result.title,
                voteAverage = result.voteAverage,
                id = result.id,
                runtime = 0,
                genres = emptyList()
            )
        }
    )
}
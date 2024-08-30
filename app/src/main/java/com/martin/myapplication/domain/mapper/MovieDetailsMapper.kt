package com.martin.myapplication.domain.mapper

import com.martin.myapplication.data.remote.model.ErrorResponse
import com.martin.myapplication.data.remote.model.MovieDetails
import com.martin.myapplication.domain.model.MovieDetailsModel
import com.slack.eithernet.ApiResult
import javax.inject.Inject

class MovieDetailsMapper @Inject constructor() {

    fun mapAsResult(response: ApiResult<MovieDetails, ErrorResponse>): ApiResult<MovieDetailsModel, ErrorResponse> {
        return when (response) {
            is ApiResult.Success -> {
                val movieModel = response.value.toMovieModel()
                ApiResult.success(movieModel)
            }

            is ApiResult.Failure -> {
                TODO()
            }
        }
    }
}

fun MovieDetails.toMovieModel(): MovieDetailsModel {
    return MovieDetailsModel(
        id = this.id,
        overview = this.overview,
        releaseDate = this.releaseDate,
        posterPath = this.posterPath,
        title = this.title,
        genres = this.genres,
        voteAverage = this.voteAverage,
        runtime = this.runtime,
        backdropPath = this.backdropPath
    )
}
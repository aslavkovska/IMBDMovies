package com.martin.myapplication.domain.mapper

import com.martin.myapplication.data.remote.model.ErrorResponse
import com.martin.myapplication.data.remote.model.SearchMovie
import com.martin.myapplication.domain.model.SearchMovieModel
import com.slack.eithernet.ApiResult
import javax.inject.Inject

class SearchedMoviesMapper @Inject constructor() {

    fun mapAsResult(response: ApiResult<SearchMovie, ErrorResponse>): ApiResult<SearchMovieModel, ErrorResponse> {
        return when (response) {
            is ApiResult.Success -> {
                val movieModel = response.value.toMovieModel()
                ApiResult.success(movieModel)
            }

            is ApiResult.Failure -> when (response) {
                is ApiResult.Failure.NetworkFailure -> error("NetworkFailure")
                is ApiResult.Failure.HttpFailure -> error("HttpFailure")
                is ApiResult.Failure.ApiFailure -> error("ApiFailure")
                is ApiResult.Failure.UnknownFailure -> error("UnknownFailure")
            }
        }
    }
}

fun SearchMovie.toMovieModel(): SearchMovieModel {
    return SearchMovieModel(
        results = this.results.map { result ->
            SearchMovieModel.Result(
                genreIds = result.genreIds,
                originalTitle = result.originalTitle,
                posterPath = result.posterPath,
                releaseDate = result.releaseDate,
                voteAverage = result.voteAverage
            )
        }
    )
}
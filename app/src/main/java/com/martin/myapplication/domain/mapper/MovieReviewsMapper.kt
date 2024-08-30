package com.martin.myapplication.domain.mapper

import com.martin.myapplication.data.remote.model.ErrorResponse
import com.martin.myapplication.data.remote.model.MovieDetails
import com.martin.myapplication.data.remote.model.MovieReviews
import com.martin.myapplication.domain.model.MovieDetailsModel
import com.martin.myapplication.domain.model.MovieReviewsModel
import com.slack.eithernet.ApiResult
import javax.inject.Inject

class MovieReviewsMapper @Inject constructor() {

    fun mapAsResult(response: ApiResult<MovieReviews, ErrorResponse>): ApiResult<MovieReviewsModel, ErrorResponse> {
        return when (response) {
            is ApiResult.Success -> {
                val movieModel = response.value.toMovieModel()
                ApiResult.success(movieModel)
            }

            is ApiResult.Failure ->  {
                response
            }
        }
    }
}

fun MovieReviews.toMovieModel(): MovieReviewsModel {
    return MovieReviewsModel(
        results = this.results.map { result ->
            MovieReviewsModel.ResultX(
                authorDetails = result.authorDetails,
                content = result.content,
                author = result.author
            )
        }
    )
}
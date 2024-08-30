package com.martin.myapplication.domain.model

import com.martin.myapplication.data.remote.model.MovieReviews.AuthorDetails
import com.martin.myapplication.data.remote.model.MovieReviews.ResultX

data class MovieReviewsModel(
    val results: List<ResultX>,
) {
    data class ResultX(
        val author: String?,
        val authorDetails: AuthorDetails,
        val content: String,
    )
}
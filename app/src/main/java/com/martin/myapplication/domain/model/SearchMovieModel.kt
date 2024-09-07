package com.martin.myapplication.domain.model

data class SearchMovieModel(
    val results: List<Result>,
) {
    data class Result(
        val genreIds: List<Int>,
        val originalTitle: String,
        val posterPath: String,
        val releaseDate: String,
        val voteAverage: Double,
    )
}
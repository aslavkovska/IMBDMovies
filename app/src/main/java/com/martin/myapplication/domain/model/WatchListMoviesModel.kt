package com.martin.myapplication.domain.model

import com.martin.myapplication.data.remote.model.MovieDetails

data class WatchListMoviesModel (
    val results: List<Result>,
){
    data class Result(
        val genreIds: List<Int>,
        val posterPath: String,
        val releaseDate: String,
        val title: String,
        val voteAverage: Double,
        val id: Int,
        var genres: List<MovieDetails.Genre>,
        var runtime: Int,
    )
}
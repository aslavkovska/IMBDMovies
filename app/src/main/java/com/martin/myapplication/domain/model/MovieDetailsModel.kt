package com.martin.myapplication.domain.model

import com.martin.myapplication.data.remote.model.MovieDetails

data class MovieDetailsModel (
    val genres: List<MovieDetails.Genre>,
    val id: Int,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val voteAverage: Double,
    val runtime: Int,
    val backdropPath: String,
)
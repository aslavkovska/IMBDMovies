package com.martin.myapplication.presentation.state

import com.martin.myapplication.domain.model.SearchMovieModel

data class SearchMoviesUiState(
    val isLoading: Boolean = false,
    val movies: List<SearchMovieModel.Result> = emptyList(),
    var error: String = "",
)
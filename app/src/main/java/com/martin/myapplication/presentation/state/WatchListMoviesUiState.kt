package com.martin.myapplication.presentation.state

import com.martin.myapplication.domain.model.WatchListMoviesModel

data class WatchListMoviesUiState(
    val isLoading: Boolean = false,
    val movies: List<WatchListMoviesModel.Result> = emptyList(),
    var error: String = "",
)
package com.martin.myapplication.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martin.myapplication.domain.usecase.SearchMoviesUseCase
import com.martin.myapplication.presentation.state.SearchMoviesUiState
import com.slack.eithernet.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchedMovies: SearchMoviesUseCase,
) : ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _state = MutableStateFlow(SearchMoviesUiState())
    val state: StateFlow<SearchMoviesUiState> = _state

    init {
        searchText
            .debounce(1000)
            .onEach { _isSearching.update { true } }
            .filter { it.isNotEmpty() }
            .onEach { query ->
                getMovieResults(query)
            }
            .onEach { _isSearching.update { false } }
            .launchIn(viewModelScope)
    }

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    fun onEmptyInput() {
        _state.update { it.copy(movies = emptyList()) }
    }

    fun getMovieResults(input: String) {
        searchedMovies(input).onEach { result ->
            _state.update { uiState ->
                when (result) {
                    is ApiResult.Success -> {
                        uiState.copy(movies = result.value.results)
                    }

                    is ApiResult.Failure -> {
                        uiState.copy(error = "An unexpected error occurred")
                    }

                    else -> uiState
                }
            }
        }.launchIn(viewModelScope)
    }
}
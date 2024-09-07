package com.martin.myapplication.domain.usecase

import com.martin.myapplication.data.remote.model.ErrorResponse
import com.martin.myapplication.data.remote.repository.MoviesRepository
import com.martin.myapplication.domain.mapper.SearchedMoviesMapper
import com.martin.myapplication.domain.model.SearchMovieModel
import com.slack.eithernet.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SearchMoviesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val searchedMoviesMapper: SearchedMoviesMapper,
) {
    operator fun invoke(input: String): Flow<ApiResult<SearchMovieModel, ErrorResponse>> = flow {
        try {
            val movies = moviesRepository.getSearchedMovies(input)
            val result = searchedMoviesMapper.mapAsResult(movies)
            emit(result)
        } catch (e: HttpException) {
            TODO()
        } catch (e: IOException) {
            TODO()
        }
    }
}
package com.martin.myapplication.domain.usecase

import com.martin.myapplication.data.remote.model.ErrorResponse
import com.martin.myapplication.data.remote.repository.MoviesRepository
import com.martin.myapplication.domain.mapper.WatchListMoviesMapper
import com.martin.myapplication.domain.model.WatchListMoviesModel
import com.slack.eithernet.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class WatchListMoviesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val watchListMovieMapper: WatchListMoviesMapper,
) {
    operator fun invoke(id: Int): Flow<ApiResult<WatchListMoviesModel, ErrorResponse>> = flow {
        try {
            val movies = moviesRepository.getWatchListMovies(id)
            val result = watchListMovieMapper.mapAsResult(movies)
            emit(result)
        } catch (e: HttpException) {
            TODO()
        } catch (e: IOException) {
            TODO()
        }
    }
}
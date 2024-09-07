package com.martin.myapplication.domain.usecase

import com.martin.myapplication.data.remote.api.WatchlistRequest
import com.martin.myapplication.data.remote.model.ErrorResponse
import com.martin.myapplication.data.remote.model.WatchlistResponse
import com.martin.myapplication.data.remote.repository.MoviesRepository
import com.slack.eithernet.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AddMovieToWatchlistUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {
    operator fun invoke(id: Int, watchlistRequest: WatchlistRequest): Flow<ApiResult<WatchlistResponse, ErrorResponse>> = flow {
        try {
            val result = moviesRepository.addMovieToWatchlist(id,watchlistRequest)
            emit(result)
        } catch (e: HttpException) {
            TODO()
        } catch (e: IOException) {
            TODO()
        }
    }
}
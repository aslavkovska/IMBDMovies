package com.martin.myapplication.domain.usecase

import com.martin.myapplication.data.remote.model.ErrorResponse
import com.martin.myapplication.data.remote.repository.MovieDetailsRepository
import com.martin.myapplication.domain.mapper.MovieDetailsMapper
import com.martin.myapplication.domain.model.MovieDetailsModel
import com.slack.eithernet.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MovieDetailsUseCase @Inject constructor(
    private val movieDetailsRepository: MovieDetailsRepository,
    private val movieDetailsMapper: MovieDetailsMapper,
) {
    operator fun invoke(id: Int): Flow<ApiResult<MovieDetailsModel, ErrorResponse>> = flow {
        try {
            val movie = movieDetailsRepository.getMovieDetails(id)
            val result = movieDetailsMapper.mapAsResult(movie)
            emit(result)
        } catch (e: HttpException) {
            TODO()
        } catch (e: IOException) {
            TODO()
        }
    }
}
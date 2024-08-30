package com.martin.myapplication.domain.usecase

import com.martin.myapplication.data.remote.model.ErrorResponse
import com.martin.myapplication.data.remote.repository.MovieDetailsRepository
import com.martin.myapplication.domain.mapper.MovieReviewsMapper
import com.martin.myapplication.domain.model.MovieReviewsModel
import com.slack.eithernet.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MovieReviewsUseCase @Inject constructor(
    private val movieDetailsRepository: MovieDetailsRepository,
    private val movieReviewsMapper: MovieReviewsMapper,
) {
    operator fun invoke(id: Int): Flow<ApiResult<MovieReviewsModel, ErrorResponse>> = flow {
        try {
            val movie = movieDetailsRepository.getMovieReviews(id)
            val result = movieReviewsMapper.mapAsResult(movie)
            emit(result)
        } catch (e: HttpException) {
            TODO()
        } catch (e: IOException) {
            TODO()
        }
    }
}
package com.martin.myapplication.data.di

import com.martin.myapplication.BuildConfig
import com.martin.myapplication.data.remote.api.MoviesApi
import com.slack.eithernet.ApiResultCallAdapterFactory
import com.slack.eithernet.ApiResultConverterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
    
    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(provideHeaderInterceptor())
            .addInterceptor(AuthInterceptor("eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIzNjE0YjlmZjg4ZTNhMTVlM2MyOTlkZmEyYzJkYzgwYSIsIm5iZiI6MTcyNDM5OTcyOC43MjMyNjgsInN1YiI6IjY2YzU5ZWYyN2EzMjFkZWFjY2FmNWI0ZCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.UX84djui0gDEOmrpaUGouI36EDMAzzJ2BktRJGQP4-c"))
            .build()
    }

    @Provides
    @Singleton
    fun provideHeaderInterceptor(): Interceptor {
        return Interceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .addHeader("accept", "application/json")
                .addHeader("content-type", "application/json")
                .addHeader("Authorization", "Bearer ${BuildConfig.MOVIES_ACCESS_KEY}")
            val request = requestBuilder.build()
            chain.proceed(request)
        }
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }


    @Provides
    @Singleton
    fun provideYourApiService(retrofit: Retrofit): MoviesApi {
        return retrofit.create(MoviesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(ApiResultConverterFactory)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(ApiResultCallAdapterFactory)
            .client(okHttpClient)
            .build()
    }
}

class AuthInterceptor(private val accessToken: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .header("Authorization", "Bearer $accessToken")
            .build()
        return chain.proceed(newRequest)
    }
}
package com.binar.challenge5.data.api

import com.binar.challenge5.data.api.model.DetailMovieResponse
import com.binar.challenge5.data.api.model.MovieResponse
import com.binar.challenge5.data.api.model.ReviewResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("tv/popular")
    suspend fun getPopularTv(): MovieResponse

    @GET("movie/now_playing")
    suspend fun getAiringMovie(): MovieResponse

    @GET("discover/movie")
    fun getDiscoverMovie(): Call<MovieResponse>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovie(): MovieResponse

    @GET("movie/top_rated")
    suspend fun getTopRatedMovie(): MovieResponse

    @GET("movie/{movieId}")
    suspend fun getDetailMovie(@Path("movieId") movieId: Int): DetailMovieResponse

    @GET("movie/{movieId}/similar")
    suspend fun getSimilarMovie(@Path("movieId") movieId: Int): MovieResponse

    @GET("movie/{movieId}/reviews")
    suspend fun getMovieReview(@Path("movieId") movieId: Int): ReviewResponse



}
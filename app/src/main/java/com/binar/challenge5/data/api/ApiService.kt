package com.binar.challenge5.data.api

import com.binar.challenge5.data.api.model.DetailMovieResponse
import com.binar.challenge5.data.api.model.MovieResponse
import com.binar.challenge5.data.api.model.ReviewResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("tv/popular")
    fun getPopularTv(): Call<MovieResponse>

    @GET("movie/now_playing")
    fun getAiringMovie(): Call<MovieResponse>

    @GET("discover/movie")
    fun getDiscoverMovie(): Call<MovieResponse>

    @GET("movie/upcoming")
    fun getUpcomingMovie(): Call<MovieResponse>

    @GET("movie/top_rated")
    fun getTopRatedMovie(): Call<MovieResponse>

    @GET("movie/{movieId}")
    fun getDetailMovie(@Path("movieId") movieId: Int): Call<DetailMovieResponse>

    @GET("movie/{movieId}/recommendations")
    fun getRecommendationsMovie(@Path("movieId") movieId: Int): Call<MovieResponse>

    @GET("movie/{movieId}/similar")
    fun getSimilarMovie(@Path("movieId") movieId: Int): Call<MovieResponse>

    @GET("movie/{movieId}/reviews")
    fun getMovieReview(@Path("movieId") movieId: Int): Call<ReviewResponse>



}
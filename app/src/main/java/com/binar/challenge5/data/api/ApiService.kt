package com.binar.challenge5.data.api

import com.binar.challenge5.data.api.model.DetailMovieResponse
import com.binar.challenge5.data.api.model.MovieResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {


    @GET("movie/now_playing")
    fun getAiringMovie(): Call<MovieResponse>

    @GET("discover/movie")
    fun getDiscoverMovie(): Call<MovieResponse>

    @GET("movie/{id}")
    fun getDetailMovie(@Path("id") id: Int): Call<DetailMovieResponse>




}
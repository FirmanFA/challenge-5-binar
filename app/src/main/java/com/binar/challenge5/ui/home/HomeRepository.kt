package com.binar.challenge5.ui.home

import com.binar.challenge5.data.api.ApiService

class HomeRepository(private val apiService: ApiService) {


    suspend fun getAiringMovies() = apiService.getAiringMovie()
    fun getDiscoverMovies() = apiService.getDiscoverMovie()
    suspend fun getUpcomingMovies() = apiService.getUpcomingMovie()
    suspend fun getTopRatedMovies() = apiService.getTopRatedMovie()


}
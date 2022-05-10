package com.binar.challenge5.ui.home

import com.binar.challenge5.data.api.ApiService

class HomeRepository(private val apiService: ApiService) {


    fun getPopularTv() = apiService.getPopularTv()
    fun getAiringMovies() = apiService.getAiringMovie()
    fun getDiscoverMovies() = apiService.getDiscoverMovie()
    fun getUpcomingMovies() = apiService.getUpcomingMovie()
    fun getTopRatedMovies() = apiService.getTopRatedMovie()


}
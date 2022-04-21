package com.binar.challenge5.ui.detail

import com.binar.challenge5.data.api.ApiService

class DetailRepository(private val apiService: ApiService) {

    fun getDetailMovies(movieId: Int) = apiService.getDetailMovie(movieId)
    fun getSimilarMovies(movieId: Int) = apiService.getSimilarMovie(movieId)

}
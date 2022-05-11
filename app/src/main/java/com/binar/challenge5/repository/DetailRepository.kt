package com.binar.challenge5.repository

import com.binar.challenge5.data.api.ApiService
import com.binar.challenge5.data.local.FavoriteDao
import com.binar.challenge5.data.local.model.Favorite

class DetailRepository(private val apiService: ApiService, private val favoriteDao: FavoriteDao) {

    suspend fun getDetailMovie(movieId: Int) = apiService.getDetailMovie(movieId)
    suspend fun getSimilarMovies(movieId: Int) = apiService.getSimilarMovie(movieId)
    suspend fun getMovieReviews(movieId: Int) = apiService.getMovieReview(movieId)
    fun getFavoriteById(movieId: Int) = favoriteDao.readFavoriteById(movieId)
    fun addToFavorite(favorite: Favorite) = favoriteDao.insertFavorite(favorite)
    fun removeFromFavorite(favorite: Favorite) = favoriteDao.deleteFavorite(favorite)

}
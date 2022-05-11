package com.binar.challenge5.repository

import androidx.lifecycle.MutableLiveData
import androidx.room.Delete
import com.binar.challenge5.data.local.FavoriteDao
import com.binar.challenge5.data.local.model.Favorite

class FavoriteRepository(private val favoriteDao: FavoriteDao) {

    suspend fun getAllFavorites() = favoriteDao.readFavorites()

}
package com.binar.challenge5.data.local

import androidx.room.*
import com.binar.challenge5.data.local.model.Favorite

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM Favorite")
    fun readFavorites(): List<Favorite>

    @Query("SELECT * FROM Favorite WHERE id=:id")
    fun readFavoriteById(id: Int): Favorite?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(favorite: Favorite):Long

    @Delete
    fun deleteFavorite(favorite: Favorite):Int
}
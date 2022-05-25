package com.binar.challenge5.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.binar.challenge5.data.local.model.Favorite
import com.binar.challenge5.data.local.model.User

@Database(entities = [User::class, Favorite::class], version = 1, exportSchema = false)
abstract class MyDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun favoriteDao(): FavoriteDao
}
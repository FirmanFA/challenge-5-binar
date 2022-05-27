package com.binar.challenge5.repository

import com.binar.challenge5.data.local.FavoriteDao
import com.binar.challenge5.data.local.UserDao
import com.binar.challenge5.data.local.model.Favorite
import com.binar.challenge5.data.local.model.User
import com.binar.challenge5.datastore.UserDataStoreManager
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class FavoriteRepositoryTest {

    private lateinit var favoriteDao: FavoriteDao

    private lateinit var favoriteRepository: FavoriteRepository

    @Before
    fun setUp() {
        favoriteDao = mockk()
        favoriteRepository = FavoriteRepository(favoriteDao)
    }

    @Test
    fun getAllFavorites() {

        val returnFavorite = mockk<List<Favorite>>()

        every {
            runBlocking {
                favoriteDao.readFavorites()
            }
        } returns returnFavorite

        runBlocking {
            favoriteRepository.getAllFavorites()
        }

        verify {
            runBlocking {
                favoriteDao.readFavorites()
            }
        }
    }
}
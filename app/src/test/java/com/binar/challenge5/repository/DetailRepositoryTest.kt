package com.binar.challenge5.repository

import com.binar.challenge5.data.api.ApiService
import com.binar.challenge5.data.api.model.DetailMovieResponse
import com.binar.challenge5.data.local.FavoriteDao
import com.binar.challenge5.datastore.UserDataStoreManager
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class DetailRepositoryTest {

    private lateinit var favoriteDao: FavoriteDao
    private lateinit var apiService: ApiService


    lateinit var detailRepository: DetailRepository

    @Before
    fun setUp() {

        favoriteDao = mockk()
        apiService = mockk()

        detailRepository = DetailRepository(apiService, favoriteDao)
    }

    @Test
    fun getDetailMovie() {
        val responseDetailMovie = mockk<DetailMovieResponse>()

        every {
            runBlocking {
                apiService.getDetailMovie(1)
            }
        } returns responseDetailMovie

        runBlocking {
            detailRepository.getDetailMovie(1)
        }

        verify {
            runBlocking {
                apiService.getDetailMovie(1)
            }
        }
    }
}
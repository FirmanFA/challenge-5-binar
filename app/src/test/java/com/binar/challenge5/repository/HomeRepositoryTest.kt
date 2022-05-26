package com.binar.challenge5.repository

import com.binar.challenge5.data.api.ApiService
import com.binar.challenge5.data.api.model.MovieResponse
import com.binar.challenge5.datastore.UserDataStoreManager
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test

class HomeRepositoryTest {


    private lateinit var userDataStoreManager: UserDataStoreManager
    private lateinit var apiService: ApiService


    lateinit var homeRepository: HomeRepository

    @Before
    fun setUp() {

        userDataStoreManager = mockk()
        apiService = mockk()

        homeRepository = HomeRepository(apiService, userDataStoreManager)

    }


    @Test
    fun getAiringMovies() {

        val responseDiscover = mockk<MovieResponse>()

        every {
            runBlocking {
                apiService.getAiringMovie()
            }
        } returns responseDiscover

        runBlocking {
            homeRepository.getAiringMovies()
        }

        verify {
            runBlocking {
                apiService.getAiringMovie()
            }
        }

    }


    @Test
    fun getUpcomingMovies() {
    }

    @Test
    fun getTopRatedMovies() {
    }

    @Test
    fun getEmail() {
    }

    @Test
    fun getNama() {
    }
}
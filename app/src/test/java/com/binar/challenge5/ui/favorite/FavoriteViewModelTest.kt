package com.binar.challenge5.ui.favorite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.binar.challenge5.data.api.Resource
import com.binar.challenge5.data.api.Status
import com.binar.challenge5.data.local.model.Favorite
import com.binar.challenge5.repository.FavoriteRepository
import com.binar.challenge5.ui.detail.DetailViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class FavoriteViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    val dispatcher = StandardTestDispatcher()

    private lateinit var repository: FavoriteRepository

    private lateinit var favoriteViewModel: FavoriteViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {

        Dispatchers.setMain(dispatcher)
        repository = mockk()
        favoriteViewModel = FavoriteViewModel(repository)

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }

    @Test
    fun getAllFavorites() {
        val allFavorites = mockk<List<Favorite>>()

        coEvery {
            repository.getAllFavorites()
        } returns allFavorites

        favoriteViewModel.allFavorites.observeForever{
            assertNotNull(it)
        }
        favoriteViewModel.getAllFavorites()
    }
}
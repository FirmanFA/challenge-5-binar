package com.binar.challenge5.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.binar.challenge5.data.api.Resource
import com.binar.challenge5.data.api.Status
import com.binar.challenge5.data.api.model.DetailMovieResponse
import com.binar.challenge5.repository.DetailRepository
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.Assert.*

import org.junit.rules.TestRule
import org.mockito.Mockito
import org.mockito.kotlin.any
import retrofit2.Response

class DetailViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    val dispatcher = StandardTestDispatcher()

    private lateinit var repository: DetailRepository
    private lateinit var observer: Observer<Resource<DetailMovieResponse>>
    private lateinit var resource: Resource<DetailMovieResponse>

    private lateinit var detailViewModel: DetailViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {

        Dispatchers.setMain(dispatcher)
        observer = mockk()
        repository = mockk()
        resource = mockk()
        detailViewModel = DetailViewModel(repository)

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }

    @Test
    fun getDetailMovie() {

        val detailMovie = mockk<DetailMovieResponse>()

        coEvery {
            repository.getDetailMovie(1)
        } returns detailMovie

        detailViewModel.detailMovie.observeForever{
            assertNotNull(it)
            assertEquals(detailViewModel.detailMovie.value?.status, Status.SUCCESS)
        }
        detailViewModel.getDetailMovie(1)

    }

    @Test
    fun getSimilarMovies() {
    }

    @Test
    fun getMovieReviews() {
    }

    @Test
    fun testGetDetailMovie() {
    }

    @Test
    fun testGetSimilarMovies() {
    }

    @Test
    fun testGetMovieReviews() {
    }

    @Test
    fun isFavoriteExist() {
    }

    @Test
    fun changeFavorite() {
    }

    @Test
    fun getFavoriteById() {
    }

    @Test
    fun addToFavorite() {
    }

    @Test
    fun removeFromFavorite() {
    }
}
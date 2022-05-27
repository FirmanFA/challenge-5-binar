package com.binar.challenge5.ui.auth

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.binar.challenge5.data.local.model.Favorite
import com.binar.challenge5.data.local.model.User
import com.binar.challenge5.repository.AuthRepository
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

class AuthViewModelTest {


    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    val dispatcher = StandardTestDispatcher()

    private lateinit var repository: AuthRepository

    private lateinit var authViewModel: AuthViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {

        Dispatchers.setMain(dispatcher)
        repository = mockk()
        authViewModel = AuthViewModel(repository)

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }

    @Test
    fun getUser() {

        val returnUser = mockk<User>()

        coEvery {
            repository.getUser("email@email.com")
        } returns returnUser

        authViewModel.user.observeForever{
            assertNotNull(it)
        }
        authViewModel.getUser("email@email.com")

    }
}
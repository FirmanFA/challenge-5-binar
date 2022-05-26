package com.binar.challenge5.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.room.Dao
import com.binar.challenge5.data.api.ApiService
import com.binar.challenge5.data.local.UserDao
import com.binar.challenge5.data.local.model.User
import com.binar.challenge5.datastore.UserDataStoreManager
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class AuthRepositoryTest {

    private lateinit var userDao: UserDao
    private lateinit var userDataStoreManager: UserDataStoreManager

    private lateinit var authRepository: AuthRepository


    @Before
    fun setUp() {

        userDao = mockk()
        userDataStoreManager = mockk()
        authRepository = AuthRepository(userDao, userDataStoreManager)

    }

    @Test
    fun login() {
        val returnLogin = mockk<User>()

        every {
            runBlocking {
                userDao.login("email", "password")
            }
        } returns returnLogin

        authRepository.login("email", "password")

        verify {
            runBlocking {
                userDao.login("email", "password")
            }
        }


    }

    @Test
    fun register() {

    }

    @Test
    fun checkEmailIfExist() {
    }

    @Test
    fun getUser() {
    }

    @Test
    fun updateUser() {
    }

    @Test
    fun updateAvatarPath() {
    }

    @Test
    fun setEmail() {
    }

    @Test
    fun setNama() {
    }

    @Test
    fun getEmail() {
    }

    @Test
    fun deletePref() {

        val returnDeletePref = mockk<Unit>()

        every {
            runBlocking {
                userDataStoreManager.deletePref()
            }
        }returns returnDeletePref

        runBlocking {
            authRepository.deletePref()
        }

        verify {
            runBlocking {
                userDataStoreManager.deletePref()
            }
        }



    }
}
package com.binar.challenge5.repository

import android.content.Context
import androidx.lifecycle.asLiveData
import com.binar.challenge5.data.local.MyDatabase
import com.binar.challenge5.data.local.UserDao
import com.binar.challenge5.data.local.model.User
import com.binar.challenge5.datastore.UserDataStoreManager
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow

class AuthRepository(private val userDao: UserDao, private val userPref: UserDataStoreManager) {

    //room
    fun login(email: String, password: String):User? = userDao.login(email, password)
    fun register(user: User):Long = userDao.insertUser(user)
    fun checkEmailIfExist(email: String): User? = userDao.checkEmailExist(email)
    suspend fun getUser(email: String): User? = userDao.getUser(email)
    fun updateUser(user: User):Int = userDao.updatetUser(user)

    //data store
    suspend fun setEmail(email: String) = userPref.setEmail(email)
    suspend fun setNama(nama: String) = userPref.setNama(nama)
    fun getEmail() = userPref.getEmail.asLiveData()
    suspend fun deletePref() = userPref.deletePref()



}
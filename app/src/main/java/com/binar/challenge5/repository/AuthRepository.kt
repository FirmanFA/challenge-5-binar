package com.binar.challenge5.repository

import android.content.Context
import com.binar.challenge5.data.local.MyDatabase
import com.binar.challenge5.data.local.UserDao
import com.binar.challenge5.data.local.model.User
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow

class AuthRepository(private val userDao: UserDao) {

    fun login(email: String, password: String):User? = userDao.login(email, password)
    fun register(user: User):Long = userDao.insertUser(user)
    fun checkEmailIfExist(email: String): User? = userDao.checkEmailExist(email)
    suspend fun getUser(email: String): User? = userDao.getUser(email)
    fun updateUser(user: User):Int = userDao.updatetUser(user)



}
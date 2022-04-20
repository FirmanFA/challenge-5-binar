package com.binar.challenge5.ui.auth

import android.content.Context
import com.binar.challenge5.data.local.MyDatabase
import com.binar.challenge5.data.local.UserDao
import com.binar.challenge5.data.local.model.User
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow

class AuthRepository(private val userDao: UserDao) {

//    private var myDatabase = MyDatabase.getInstance(context)
//
//    suspend fun login(email: String, password: String): User? = coroutineScope {
//        return@coroutineScope myDatabase?.userDao()?.login(email, password)
//    }
//
//    suspend fun register(user: User): Long? = coroutineScope {
//        return@coroutineScope myDatabase?.userDao()?.insertUser(user)
//    }
//
//    suspend fun checkEmailIfExist(email: String):User? = coroutineScope {
//        return@coroutineScope myDatabase?.userDao()?.checkEmailExist(email)
//    }

    fun login(email: String, password: String):User? = userDao.login(email, password)
    fun register(user: User):Long = userDao.insertUser(user)
    fun checkEmailIfExist(email: String): User? = userDao.checkEmailExist(email)
    fun getUser(email: String): User = userDao.getUser(email)
    fun updateUser(user: User):Int = userDao.updatetUser(user)



}
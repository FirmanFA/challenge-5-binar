package com.binar.challenge5.ui.auth

import android.content.Context
import com.binar.challenge5.data.local.MyDatabase
import com.binar.challenge5.data.local.model.User
import kotlinx.coroutines.coroutineScope

class AuthRepository(context: Context) {

    private var myDatabase = MyDatabase.getInstance(context)

    suspend fun login(email: String, password: String): User? = coroutineScope {
        return@coroutineScope myDatabase?.userDao()?.login(email, password)
    }

    suspend fun register(user: User): Long? = coroutineScope {
        return@coroutineScope myDatabase?.userDao()?.insertUser(user)
    }

    suspend fun checkEmailIfExist(email: String):User? = coroutineScope {
        return@coroutineScope myDatabase?.userDao()?.checkEmailExist(email)
    }


}
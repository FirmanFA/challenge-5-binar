package com.binar.challenge5.data.local

import androidx.room.*
import com.binar.challenge5.data.local.model.User

@Dao
interface UserDao {

    @Query("SELECT * FROM User WHERE email = :email AND password = :password")
    fun login(email: String, password: String): User?

    @Query("SELECT * FROM User WHERE email = :email")
    fun checkEmailExist(email: String):User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User):Long

    @Query("SELECT * FROM User WHERE email = :email")
    fun getUser(email: String): User?

    @Update
    fun updatetUser(user: User):Int

}
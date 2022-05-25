package com.binar.challenge5.di

import androidx.room.Room
import com.binar.challenge5.data.local.MyDatabase
import org.koin.dsl.module

val databaseModule = module {

    single {
        Room.databaseBuilder(get()
            , MyDatabase::class.java, "mydatabase.db").build()
    }

    single {
        get<MyDatabase>().userDao()
    }

    single {
        get<MyDatabase>().favoriteDao()
    }

}
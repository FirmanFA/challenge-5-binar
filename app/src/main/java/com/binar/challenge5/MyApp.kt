package com.binar.challenge5

import android.app.Application
import com.binar.challenge5.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApp: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MyApp)
            modules(networkModule, databaseModule, datastoreModule, repositoryModule, viewModelModule)
        }
    }

}
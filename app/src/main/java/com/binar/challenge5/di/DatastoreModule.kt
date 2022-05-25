package com.binar.challenge5.di

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import com.binar.challenge5.datastore.UserDataStoreManager
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module


private const val DATA_STORE_NAME = "userdatastore"

val datastoreModule = module {

    single {
        PreferenceDataStoreFactory.create {
            androidContext().preferencesDataStoreFile(DATA_STORE_NAME)
        }
    }

    singleOf(::UserDataStoreManager)
}
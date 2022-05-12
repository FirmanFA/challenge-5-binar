package com.binar.challenge5.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserDataStoreManager(private val context: Context) {

    val getEmail: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[emailKey] ?: ""
        }

    val getNama: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[namaKey] ?: ""
        }

    suspend fun setEmail(email: String){
        context.dataStore.edit {
            it[emailKey] = email
        }
    }

    suspend fun setNama(nama: String){
        context.dataStore.edit {
            it[namaKey] = nama
        }
    }

    suspend fun deletePref(){
        context.dataStore.edit {
            it.clear()
        }
    }

    companion object{
        private const val EMAIL_KEY = "emailkey"
        private const val NAMA_KEY = "jumlahkey"
        private const val DATA_STORE_NAME = "userdatastore"
        val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATA_STORE_NAME)
        val namaKey = stringPreferencesKey(NAMA_KEY)
        val emailKey = stringPreferencesKey(EMAIL_KEY)
    }
}
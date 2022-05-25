package com.binar.challenge5.ui.favorite

import androidx.lifecycle.*
import com.binar.challenge5.data.local.model.Favorite
import com.binar.challenge5.repository.FavoriteRepository
import kotlinx.coroutines.launch

class FavoriteViewModel(private val repository: FavoriteRepository): ViewModel() {

    private val _allFavorites: MutableLiveData<List<Favorite?>> = MutableLiveData()
    val allFavorites: LiveData<List<Favorite?>> = _allFavorites

    fun getAllFavorites(){
        viewModelScope.launch {
            val allFavorites = repository.getAllFavorites()
            _allFavorites.postValue(allFavorites)
        }
    }

}
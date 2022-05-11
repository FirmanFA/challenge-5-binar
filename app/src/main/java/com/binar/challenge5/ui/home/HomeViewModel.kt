package com.binar.challenge5.ui.home

import android.util.Log
import androidx.lifecycle.*
import com.binar.challenge5.data.api.ApiClient
import com.binar.challenge5.data.api.Resource
import com.binar.challenge5.data.api.model.DetailMovieResponse
import com.binar.challenge5.data.api.model.MovieResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val repository: HomeRepository): ViewModel() {


    private val _airingMovies = MutableLiveData<Resource<MovieResponse>>()
    val airingMovies: LiveData<Resource<MovieResponse>> get() = _airingMovies

    private val _upcomingMovies = MutableLiveData<Resource<MovieResponse>>()
    val upcomingMovies: LiveData<Resource<MovieResponse>> get() = _upcomingMovies

    private val _topRatedMovies = MutableLiveData<Resource<MovieResponse>>()
    val topRatedMovies: LiveData<Resource<MovieResponse>> get() = _topRatedMovies

     fun getAiringMovies(){
        viewModelScope.launch {
            _airingMovies.postValue(Resource.loading())
            try {
                _airingMovies.postValue(Resource.success(repository.getAiringMovies()))
            }catch (exp: Exception){
                _airingMovies.postValue(Resource.error(exp.localizedMessage ?: "Error occured"))
            }
        }
    }

     fun getUpcomingMovies(){
        viewModelScope.launch {
            _upcomingMovies.postValue(Resource.loading())
            try {
                _upcomingMovies.postValue(Resource.success(repository.getUpcomingMovies()))
            }catch (exp: Exception){
                _upcomingMovies.postValue(Resource.error(exp.localizedMessage ?: "Error occured"))
            }
        }
    }

    fun getTopRatedMovies(){
        viewModelScope.launch {
            _topRatedMovies.postValue(Resource.loading())
            try {
                _topRatedMovies.postValue(Resource.success(repository.getTopRatedMovies()))
            }catch (exp: Exception){
                _topRatedMovies.postValue(Resource.error(exp.localizedMessage ?: "Error occured"))
            }
        }
    }

    val errorDiscover: MutableLiveData<String> = MutableLiveData()
    val isLoadingDiscover = MutableLiveData<Boolean>()
    private val _discoverMovies: MutableLiveData<MovieResponse> by lazy {
        MutableLiveData<MovieResponse>().also {
            getDiscoverMovies()
        }
    }
    val discoverMovies: LiveData<MovieResponse> = _discoverMovies

    private fun getDiscoverMovies(){
        isLoadingDiscover.postValue(true)
        repository.getDiscoverMovies().enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                isLoadingDiscover.postValue(false)
                if (response.code() == 200){
                    _discoverMovies.postValue(response.body())
                }else{
                    errorDiscover.postValue("Error")
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                isLoadingDiscover.postValue(false)
            }
        })
    }


}

class HomeViewModelFactory(private val repository: HomeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
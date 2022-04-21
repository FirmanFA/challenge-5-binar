package com.binar.challenge5.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.binar.challenge5.data.api.ApiClient
import com.binar.challenge5.data.api.model.DetailMovieResponse
import com.binar.challenge5.data.api.model.MovieResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val repository: HomeRepository): ViewModel() {

    val errorDiscover: MutableLiveData<String> = MutableLiveData()
    val isLoadingDiscover = MutableLiveData<Boolean>()
    private val _discoverMovies: MutableLiveData<MovieResponse> by lazy {
        MutableLiveData<MovieResponse>().also {
            getDiscoverMovies()
        }
    }
    val discoverMovies: LiveData<MovieResponse> = _discoverMovies

    val errorAiring: MutableLiveData<String> = MutableLiveData()
    val isLoadingAiring = MutableLiveData<Boolean>()
    private val _airingMovies: MutableLiveData<MovieResponse> by lazy {
        MutableLiveData<MovieResponse>().also {
            getAiringMovies()
        }
    }
    val airingMovies: LiveData<MovieResponse> = _airingMovies


    val errorUpcoming: MutableLiveData<String> = MutableLiveData()
    val isLoadingUpcoming = MutableLiveData<Boolean>()
    private val _upcomingMovies: MutableLiveData<MovieResponse> by lazy {
        MutableLiveData<MovieResponse>().also {
            getUpcomingMovies()
        }
    }

    val upcomingMovies: LiveData<MovieResponse> = _upcomingMovies


    val errorTopRated: MutableLiveData<String> = MutableLiveData()
    val isLoadingTopRated = MutableLiveData<Boolean>()
    private val _topRatedMovies: MutableLiveData<MovieResponse> by lazy {
        MutableLiveData<MovieResponse>().also {
            getTopRatedMovies()
        }
    }
    val topRatedMovies: LiveData<MovieResponse> = _topRatedMovies


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

    private fun getAiringMovies(){
        isLoadingAiring.postValue(true)
        repository.getAiringMovies().enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                isLoadingAiring.postValue(false)
                if (response.code() == 200){
                    _airingMovies.postValue(response.body())
                }else{
                    errorAiring.postValue("Error")
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                isLoadingAiring.postValue(false)
            }
        })
    }

    private fun getUpcomingMovies() {
        isLoadingUpcoming.postValue(true)
        repository.getUpcomingMovies().enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                isLoadingUpcoming.postValue(false)
                if (response.code() == 200){
                    _upcomingMovies.postValue(response.body())
                }else{
                    errorUpcoming.postValue("Error")
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                isLoadingUpcoming.postValue(false)
            }
        })
    }
    private fun getTopRatedMovies() {
        isLoadingTopRated.postValue(true)
        repository.getTopRatedMovies().enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                isLoadingTopRated.postValue(false)
                if (response.code() == 200){
                    _topRatedMovies.postValue(response.body())
                }else{
                    errorTopRated.postValue("Error")
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                isLoadingTopRated.postValue(false)
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
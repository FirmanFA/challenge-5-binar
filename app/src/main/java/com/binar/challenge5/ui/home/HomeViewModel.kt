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

    val error: MutableLiveData<String> = MutableLiveData()
    val isLoading = MutableLiveData<Boolean>()
    private val _airingMovies: MutableLiveData<MovieResponse> by lazy {
        MutableLiveData<MovieResponse>().also {
            getAiringMovies()
        }
    }
    val airingMovies: LiveData<MovieResponse> = _airingMovies



    val errorDetail: MutableLiveData<String> = MutableLiveData()
    val isLoadingDetail = MutableLiveData<Boolean>()
    private val _detailMovie: MutableLiveData<DetailMovieResponse> = MutableLiveData()
    val detailMovie: LiveData<DetailMovieResponse> = _detailMovie

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
        isLoading.postValue(true)
        repository.getAiringMovies().enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                isLoading.postValue(false)
                if (response.code() == 200){
                    _airingMovies.postValue(response.body())
                }else{
                    error.postValue("Error")
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                isLoading.postValue(false)
            }
        })
    }



    fun getDetailMovies(id: Int){
        isLoadingDetail.postValue(true)
        ApiClient.instance.getDetailMovie(id).enqueue(object : Callback<DetailMovieResponse> {
            override fun onResponse(call: Call<DetailMovieResponse>, response: Response<DetailMovieResponse>) {
                isLoading.postValue(false)
                if (response.code() == 200){
                    _detailMovie.postValue(response.body())
                }else{
                    errorDetail.postValue("Error")
                }
            }

            override fun onFailure(call: Call<DetailMovieResponse>, t: Throwable) {
                isLoadingDetail.postValue(false)
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
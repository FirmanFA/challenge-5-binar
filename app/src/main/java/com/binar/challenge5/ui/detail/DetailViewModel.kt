package com.binar.challenge5.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.binar.challenge5.data.api.ApiClient
import com.binar.challenge5.data.api.model.DetailMovieResponse
import com.binar.challenge5.data.api.model.MovieResponse
import com.binar.challenge5.data.api.model.ReviewResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(private val repository: DetailRepository):ViewModel() {

    val errorDetail: MutableLiveData<String> = MutableLiveData()
    val isLoadingDetail = MutableLiveData<Boolean>()
    private val _detailMovie: MutableLiveData<DetailMovieResponse> = MutableLiveData()
    val detailMovie: LiveData<DetailMovieResponse> = _detailMovie

    val errorSimilar: MutableLiveData<String> = MutableLiveData()
    val isLoadingSimilar = MutableLiveData<Boolean>()
    private val _similarMovies: MutableLiveData<MovieResponse> = MutableLiveData()
    val similarMovies: LiveData<MovieResponse> = _similarMovies

    val errorReview: MutableLiveData<String> = MutableLiveData()
    val isLoadingReview = MutableLiveData<Boolean>()
    private val _movieReviews: MutableLiveData<ReviewResponse> = MutableLiveData()
    val movieReviews: LiveData<ReviewResponse> = _movieReviews

    fun getDetailMovies(movieId: Int){
        isLoadingDetail.postValue(true)
        repository.getDetailMovies(movieId).enqueue(object : Callback<DetailMovieResponse> {
            override fun onResponse(call: Call<DetailMovieResponse>, response: Response<DetailMovieResponse>) {
                isLoadingDetail.postValue(false)
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

    fun getSimilarMovies(movieId: Int){
        isLoadingSimilar.postValue(true)
        repository.getSimilarMovies(movieId).enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                isLoadingSimilar.postValue(false)
                if (response.code() == 200){
                    _similarMovies.postValue(response.body())
                }else{
                    errorSimilar.postValue("Error")
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                isLoadingSimilar.postValue(false)
            }
        })
    }

    fun getMovieReviews(movieId: Int){
        isLoadingReview.postValue(true)
        repository.getMovieReviews(movieId).enqueue(object : Callback<ReviewResponse> {
            override fun onResponse(call: Call<ReviewResponse>, response: Response<ReviewResponse>) {
                isLoadingReview.postValue(false)
                if (response.code() == 200){
                    _movieReviews.postValue(response.body())
                }else{
                    errorReview.postValue("Error")
                }
            }

            override fun onFailure(call: Call<ReviewResponse>, t: Throwable) {
                isLoadingReview.postValue(false)
            }
        })
    }


}

class DetailViewModelFactory(private val repository: DetailRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
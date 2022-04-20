package com.binar.challenge5.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.binar.challenge5.data.api.ApiClient
import com.binar.challenge5.data.api.model.DetailMovieResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(private val repository: DetailRepository):ViewModel() {

    val errorDetail: MutableLiveData<String> = MutableLiveData()
    val isLoadingDetail = MutableLiveData<Boolean>()
    private val _detailMovie: MutableLiveData<DetailMovieResponse> = MutableLiveData()
    val detailMovie: LiveData<DetailMovieResponse> = _detailMovie

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
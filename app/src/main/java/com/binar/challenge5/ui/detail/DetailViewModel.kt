package com.binar.challenge5.ui.detail

import androidx.lifecycle.*
import com.binar.challenge5.data.api.Resource
import com.binar.challenge5.data.api.model.DetailMovieResponse
import com.binar.challenge5.data.api.model.MovieResponse
import com.binar.challenge5.data.api.model.ReviewResponse
import com.binar.challenge5.data.local.model.Favorite
import com.binar.challenge5.repository.DetailRepository
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: DetailRepository):ViewModel() {


    private val _detailMovie = MutableLiveData<Resource<DetailMovieResponse>>()
    val detailMovie: LiveData<Resource<DetailMovieResponse>> get() = _detailMovie

    private val _similarMovies = MutableLiveData<Resource<MovieResponse>>()
    val similarMovies: LiveData<Resource<MovieResponse>> get() = _similarMovies

    private val _movieReviews = MutableLiveData<Resource<ReviewResponse>>()
    val movieReviews: LiveData<Resource<ReviewResponse>> get() = _movieReviews

    fun getDetailMovie(movieId: Int){
        viewModelScope.launch {
            _detailMovie.postValue(Resource.loading())
            try {
                _detailMovie.postValue(Resource.success(repository.getDetailMovie(movieId)))
            }catch (exp: Exception){
                _detailMovie.postValue(Resource.error(exp.localizedMessage ?: "Error occured"))
            }
        }
    }

    fun getSimilarMovies(movieId: Int){
        viewModelScope.launch {
            _similarMovies.postValue(Resource.loading())
            try {
                _similarMovies.postValue(Resource.success(repository.getSimilarMovies(movieId)))
            }catch (exp: Exception){
                _similarMovies.postValue(Resource.error(exp.localizedMessage ?: "Error occured"))
            }
        }
    }
    fun getMovieReviews(movieId: Int){
        viewModelScope.launch {
            _movieReviews.postValue(Resource.loading())
            try {
                _movieReviews.postValue(Resource.success(repository.getMovieReviews(movieId)))
            }catch (exp: Exception){
                _movieReviews.postValue(Resource.error(exp.localizedMessage ?: "Error occured"))
            }
        }
    }

    //favorite teritory
    private val _isFavoriteExist = MutableLiveData<Boolean>()
    val isFavoriteExist = _isFavoriteExist

    fun changeFavorite(state: Boolean){
        _isFavoriteExist.postValue(state)
    }

    fun getFavoriteById(movieId: Int) = repository.getFavoriteById(movieId)
    fun addToFavorite(favorite: Favorite) = repository.addToFavorite(favorite)
    fun removeFromFavorite(favorite: Favorite) = repository.removeFromFavorite(favorite)

}
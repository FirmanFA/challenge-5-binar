package com.binar.challenge5.ui.home

import androidx.lifecycle.*
import com.binar.challenge5.data.api.Resource
import com.binar.challenge5.data.api.model.MovieResponse
import com.binar.challenge5.repository.HomeRepository
import kotlinx.coroutines.launch

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

    private val _discoverMovies = MutableLiveData<Resource<MovieResponse>>()
    val discoverMovies: LiveData<Resource<MovieResponse>> = _discoverMovies

    fun getDiscoverMovies(){
        viewModelScope.launch {
            _discoverMovies.postValue(Resource.loading())
            try {
                val dataDiscover = repository.getDiscoverMovies()
                val successResource = Resource.success(dataDiscover)
                _discoverMovies.postValue(successResource)
            }catch (exp: Exception){
                _discoverMovies.postValue(Resource.error(exp.localizedMessage ?: "Error occured"))
            }
        }
    }

//    val errorDiscover: MutableLiveData<String> = MutableLiveData()
//    val isLoadingDiscover = MutableLiveData<Boolean>()
//    private val _discoverMovies: MutableLiveData<MovieResponse> by lazy {
//        MutableLiveData<MovieResponse>().also {
//            getDiscoverMovies()
//        }
//    }
//    val discoverMovies: LiveData<MovieResponse> = _discoverMovies
//
//    val debug: MutableLiveData<String> = MutableLiveData()

//    fun getDiscoverMovies(){
//        viewModelScope.launch {
//            isLoadingDiscover.postValue(true)
//            Log.d("getdiscover", "getDiscoverMovies: loading")
//            try {
//                val dataDiscover = repository.getDiscoverMovies()
//                _discoverMovies.postValue(dataDiscover)
////                _discoverMovies.postValue(Resource.success(repository.getDiscoverMovies()))
//                isLoadingDiscover.postValue(false)
//                Log.d("getdiscover", "getDiscoverMovies: try")
//            }catch (exp: Exception){
//                debug.postValue("masuk catch")
//                errorDiscover.postValue(exp.localizedMessage)
//                Log.d("getdiscover", "getDiscoverMovies: catch")
//            }
//        }
//    }

//    val errorDiscover: MutableLiveData<String> = MutableLiveData()
//    val isLoadingDiscover = MutableLiveData<Boolean>()
//    private val _discoverMovies: MutableLiveData<MovieResponse> by lazy {
//        MutableLiveData<MovieResponse>().also {
//            getDiscoverMovies()
//        }
//    }
//    val discoverMovies: LiveData<MovieResponse> = _discoverMovies
//
//    private fun getDiscoverMovies(){
//        isLoadingDiscover.postValue(true)
//        repository.getDiscoverMovies().enqueue(object : Callback<MovieResponse> {
//            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
//                isLoadingDiscover.postValue(false)
//                if (response.code() == 200){
//                    _discoverMovies.postValue(response.body())
//                }else{
//                    errorDiscover.postValue("Error")
//                }
//            }
//
//            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
//                isLoadingDiscover.postValue(false)
//            }
//        })
//    }

    //user preference
    val namaPreference = repository.getNama()
    val emailPreferences = repository.getEmail()
    fun deletePref() = viewModelScope.launch {
        repository.deletePref()
    }


}

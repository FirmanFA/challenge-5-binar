package com.binar.challenge5.ui.auth

import androidx.lifecycle.*
import com.binar.challenge5.data.local.model.Favorite
import com.binar.challenge5.data.local.model.User
import com.binar.challenge5.repository.AuthRepository
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: AuthRepository): ViewModel() {

    private val _user: MutableLiveData<User?>  = MutableLiveData()
    val user: LiveData<User?> = _user

    fun getUser(email: String){
        viewModelScope.launch {
            val newUser = repository.getUser(email)
            _user.postValue(newUser)
        }
    }

    fun updateUser(user: User) = repository.updateUser(user)

    fun login(email: String, password: String) = repository.login(email, password)

    fun register(user: User) = repository.register(user)

    fun checkIfEmailExist(email: String) = repository.checkEmailIfExist(email)



}

class AuthViewModelFactory(private val repository: AuthRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AuthViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}




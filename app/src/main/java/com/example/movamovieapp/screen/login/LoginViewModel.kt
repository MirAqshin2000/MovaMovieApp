package com.example.movamovieapp.screen.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movamovieapp.api.NetworkResource
import com.example.movamovieapp.di.AuthRespository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRespository: AuthRespository,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    val loginUi = MutableLiveData<LoginUi>()

    fun loginUser(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            loginUi.value = LoginUi.Error("Email and password cannot be empty")
            return
        }

        viewModelScope.launch {
            authRespository.login(email, password).collect { result ->
                when (result) {
                    is NetworkResource.Loading -> {
                        loginUi.value = LoginUi.Loading
                    }

                    is NetworkResource.Success -> {
                        loginUi.value = LoginUi.Success("Login Successful")
                    }

                    is NetworkResource.Error -> {
                        loginUi.value = LoginUi.Error(result.message ?: "Something went wrong")
                    }
                }
            }
        }
    }
}


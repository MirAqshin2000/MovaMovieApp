package com.example.movamovieapp.login

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

        viewModelScope.launch {
            authRespository.login(email, password).collect {

                when (it) {
                    is NetworkResource.Error -> {
                        loginUi.value = LoginUi.Error(it.message.toString())
                    }

                    is NetworkResource.Loading -> {
                        loginUi.value = LoginUi.Loading
                    }

                    is NetworkResource.Success -> {
                        loginUi.value = LoginUi.Success("Login Successful")

                    }
                }


            }


        }
    }
}



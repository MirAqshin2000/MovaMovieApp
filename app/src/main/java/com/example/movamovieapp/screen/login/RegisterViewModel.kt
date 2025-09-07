package com.example.movamovieapp.screen.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movamovieapp.api.NetworkResource
import com.example.movamovieapp.di.AuthRespository
import com.example.movamovieapp.util.SharedPrefManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authrepository: AuthRespository,
    private val prefManager: SharedPrefManager
) : ViewModel() {
    val registerUi = MutableLiveData<RegisterUiState>()


    fun registerUser(email: String, password: String) {

        if (email.isNotEmpty() && password.isNotEmpty()) {
            viewModelScope.launch {
                authrepository.register(email, password).collect {
                    when (it) {

                        is NetworkResource.Error -> {
                            registerUi.value = RegisterUiState.Error(it.message.toString())

                        }

                        is NetworkResource.Loading -> {
                            registerUi.value = RegisterUiState.Loading
                        }

                        is NetworkResource.Success -> {
                            registerUi.value =
                                RegisterUiState.Success(it.data?.user?.email.toString())
                            prefManager.saveIsLoggedIn(true)
                        }

                        else -> {
                            registerUi.value = RegisterUiState.Error("Something went wrong")
                        }


                    }

                }
            }


        }
    }

    sealed class RegisterUiState {
        data object Loading : RegisterUiState()
        data class Success(val email: String) : RegisterUiState()
        data class Error(val errormessage: String) : RegisterUiState()
        data class EmptyFields(val emptyMessage: String) : RegisterUiState()
    }
}

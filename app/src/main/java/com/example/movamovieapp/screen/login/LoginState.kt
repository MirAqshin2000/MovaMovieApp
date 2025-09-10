package com.example.movamovieapp.screen.login


sealed class LoginUi {
    object Loading : LoginUi()
    data class Success(val successMessage: String,val email: String) : LoginUi()
    data class Error(val errorMessage: String) : LoginUi()
}

sealed class RegisterUi {
    object Loading : RegisterUi()
    data class Success(val successMessage: String) : RegisterUi()
    data class Error(val errorMessage: String) : RegisterUi()
}
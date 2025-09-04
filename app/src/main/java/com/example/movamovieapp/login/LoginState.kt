package com.example.movamovieapp.login


sealed class LoginUi {
    object Loading : LoginUi()
    data class Success(val successMessage: String) : LoginUi()
    data class Error(val errorMessage: String) : LoginUi()
}
sealed class RegisterUi {
    object Loading : RegisterUi()
    data class Success(val successMessage: String) : RegisterUi()
    data class Error(val errorMessage: String) : RegisterUi()
}
package com.example.movamovieapp.home

import com.example.movamovieapp.model.Result

sealed class MovieState {
    data object Loading : MovieState()
    data class Success(val movieList: List<Result>) : MovieState()
    data class Error(val message: String) : MovieState()
}
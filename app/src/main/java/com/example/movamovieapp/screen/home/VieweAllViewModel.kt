package com.example.movamovieapp.screen.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movamovieapp.api.ApiService
import com.example.movamovieapp.model.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VieweAllViewModel @Inject constructor(
    private val api: ApiService

) : ViewModel() {
    private val movies1 = MutableLiveData<List<Result>>()

    val movies: LiveData<List<Result>> = movies1

    private var originalList: List<Result> = emptyList()


    fun getMovies(category: String) {
        viewModelScope.launch {
            try {
                val response = when (category) {
                    "now_playing" -> api.getNowPlayingMovies()
                    "popular" -> api.getPopularMovies()
                    "top_rated" -> api.getTopRatedMovies()
                    "upcoming" -> api.getUpcomingMovies()
                    else -> throw IllegalArgumentException("Invalid category")
                }
                if (response.isSuccessful) {
                    movies1.value = response.body()?.results ?: emptyList()
                    originalList = response.body()?.results ?: emptyList()
                } else {
                    movies1.value = emptyList()
                }


            } catch (e: Exception) {
                e.localizedMessage.toString()
            }
        }
    }

    fun searchMovies(query: String) {
        movies1.value = if (query.isEmpty()) {
            originalList
        } else {
            originalList.filter {
                it.title.contains(query, ignoreCase = true)
            }
        }
    }
}
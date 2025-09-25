package com.example.movamovieapp.screen.explore

import android.R.attr.id
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movamovieapp.di.MovieRepository
import com.example.movamovieapp.model.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel@Inject constructor(
    val repository: MovieRepository

) : ViewModel() {

    val allmovies = MutableLiveData<List<Result>>()
    val searchmovie = MutableLiveData<List<Result>>()
    val searchResults = MutableLiveData<List<Result>>()

    var isSearching = false


    val error = MutableLiveData<String>()
    val loading = MutableLiveData<Boolean>()
    val success = MutableLiveData<Boolean>()


    fun searchMovies(title: String) {
        if (title.isEmpty()) {
            isSearching = false
            searchResults.value = emptyList()
            return
        }

        isSearching = true
        viewModelScope.launch {
            loading.value = true
            try {
                repository.searchMovies(title).collect { response ->
                    if (response.isSuccessful) {
                        response.body()?.results?.let {
                            searchResults.value = it  // <- search üçün ayrıca LiveData
                        }
                    } else {
                        error.value = "Search failed: ${response.code()}"
                    }
                }
            } catch (e: Exception) {
                error.value = e.localizedMessage
            } finally {
                loading.value = false
            }
        }
    }


    fun getMovie() {
        viewModelScope.launch  {
            loading.value = true
            try {
                repository.getPopularMoviesFlow().collect { response ->
                    if (response.isSuccessful) {
                        response.body()?.results?.let {
                            allmovies.value = it
                        }
                    }
                }
            } catch (e: Exception) {
                error.value = e.localizedMessage
            }
        }
    }


}


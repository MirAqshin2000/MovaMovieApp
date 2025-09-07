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

    val error = MutableLiveData<String>()
    val loading = MutableLiveData<Boolean>()
    val success = MutableLiveData<Boolean>()


    fun searchMovies(title: String) {
        viewModelScope.launch {
            loading.value = true


            try {
                repository.searchMovies(title).collect { response ->
                    if (response.isSuccessful) {
                        response.body()?.results?.let {
                            allmovies.value = it
                            loading.value = false


                        }
                    }
                }
            } catch (e: Exception) {
                error.value = e.localizedMessage
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


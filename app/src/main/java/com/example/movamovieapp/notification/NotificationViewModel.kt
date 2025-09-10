package com.example.movamovieapp.notification

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movamovieapp.api.ApiService
import com.example.movamovieapp.di.MovieRepository
import com.example.movamovieapp.model.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
val repository: MovieRepository ,
val api:ApiService):ViewModel(){
    val allmovies = MutableLiveData<List<Result>>()

    fun getMovie(){
        viewModelScope.launch {
            repository.getPopularMoviesFlow()
            try {
                repository.getPopularMoviesFlow().collect { response ->
                    if (response.isSuccessful) {
                        response.body()?.results?.let {movies->
                            allmovies.postValue(movies)



                        }

                    }
                }
    }catch (e:Exception){
        e.localizedMessage.toString()
    }
        }

            }
}
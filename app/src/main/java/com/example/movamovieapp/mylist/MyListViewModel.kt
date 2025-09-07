package com.example.movamovieapp.mylist

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movamovieapp.di.MovieRepository
import com.example.movamovieapp.model.MyListModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MyListViewModel @Inject constructor(
    val respository: MovieRepository
) : ViewModel() {
    val movies = MutableLiveData<List<MyListModel>>()


    fun getAllMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            val movalist = respository.getAllMovies()
            withContext(Dispatchers.Main) {
                movies.value = movalist
            }

            Log.e("gelenData", movalist.toString())

        }
    }

    fun deleteMovie(id: Int) {
        viewModelScope.launch {
            respository.deleteMovie(id)
            val updatelsit = respository.getAllMovies()
            withContext(Dispatchers.Main) {
                movies.value = updatelsit
            }

        }

    }

}
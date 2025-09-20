package com.example.movamovieapp.screen.mylist

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movamovieapp.di.MovieRepository
import com.example.movamovieapp.local.MovieDao
import com.example.movamovieapp.model.MyListModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MyListViewModel @Inject constructor(
    val respository: MovieRepository,
    val dao: MovieDao
) : ViewModel() {
    val movies = MutableLiveData<List<MyListModel>>()



    fun searchMovies(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            movies.postValue(respository.searchMylist(query))
        }
    }

    fun getAllMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            movies.postValue(respository.getAllMovies())

            }


        }

    fun deleteMovie(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            respository.deleteMovie(id)
            movies.postValue(respository.getAllMovies())
        }
    }

    fun addMovie(movie: MyListModel) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.addMovie(movie)
getAllMovies()
        }
    }
    }


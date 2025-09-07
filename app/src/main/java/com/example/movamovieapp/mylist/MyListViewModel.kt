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
            movies.postValue(respository.getAllMovies())

            }


        }

    fun deleteMovie(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            respository.deleteMovie(id)
            movies.postValue(respository.getAllMovies())
        }
    }
    }


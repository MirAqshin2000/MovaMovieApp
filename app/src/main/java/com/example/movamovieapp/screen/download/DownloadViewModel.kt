package com.example.movamovieapp.screen.download

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movamovieapp.di.MovieRepository
import com.example.movamovieapp.model.DownloadModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DownloadViewModel @Inject constructor(
    val respository: MovieRepository
) : ViewModel() {
    val downloads = MutableLiveData<List<DownloadModel>>()


    fun getDownloads() {
        viewModelScope.launch(Dispatchers.IO) {
            respository.getDownloadMovies().collect {
                downloads.postValue(it)

            }
        }
    }



    fun deleteDownload(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            respository.deleteDownload(id)
            getDownloads()
        }
    }
}





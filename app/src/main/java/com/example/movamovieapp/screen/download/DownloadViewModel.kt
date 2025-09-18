package com.example.movamovieapp.screen.download

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movamovieapp.di.MovieRepository
import com.example.movamovieapp.local.DownloadDao
import com.example.movamovieapp.model.DownloadModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DownloadViewModel @Inject constructor(
   private  val respository: MovieRepository
) : ViewModel() {
    val downloads = MutableLiveData<List<DownloadModel>>()
    val error = MutableLiveData<String>()
    val loading = MutableLiveData<Boolean>()
    val success = MutableLiveData<Boolean>()


    fun searchDownload(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            downloads.postValue(respository.searchDownload(query))

        }
    }



   fun getDownloads() {
       viewModelScope.launch(Dispatchers.IO) {
           downloads.postValue(respository.getDownloadMovies())

       }
   }



    fun deleteDownload(id: Int) {
        viewModelScope.launch(Dispatchers.IO){
            respository.deleteDownload(id)
            downloads.postValue(respository.getDownloadMovies())
        }

    }


}





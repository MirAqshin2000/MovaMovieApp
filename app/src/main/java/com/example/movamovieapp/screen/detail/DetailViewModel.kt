package com.example.movamovieapp.screen.detail


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movamovieapp.di.MovieRepository
import com.example.movamovieapp.model.DownloadModel
import com.example.movamovieapp.model.MyListModel
import com.example.movamovieapp.model.Result
import com.example.movamovieapp.model.Review
import com.example.movamovieapp.model.detail.DetailResponse
import com.example.movamovieapp.model.detail.credits.CreditResponse
import com.example.movamovieapp.model.video.Video

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    val respository: MovieRepository
) : ViewModel() {
    val credits = MutableLiveData<CreditResponse>()
    val detail = MutableLiveData<DetailResponse>()
    val error = MutableLiveData<String>()
    val loading = MutableLiveData<Boolean>()
    val comments = MutableLiveData<List<Review>>()
    val more = MutableLiveData<List<Result>>()
    val trailer = MutableLiveData<List<Video>>()
    val movie= MutableLiveData<Result>()

    fun getMovieDetail(id: Int) {
        loading.value = true
        viewModelScope.launch {
            try {
                respository.getMovieDetailFlow(id).collect { response ->
                    if (response.isSuccessful) {
                        response.body()?.let { data ->
                            loading.value = false
                            detail.value = data
                        }
                    } else {
                        error.value = response.message()
                    }
                }

            } catch (e: Exception) {
                loading.value = false
                error.value = e.localizedMessage
            }
        }
    }


    fun getMovieCredits(id: Int) {
        loading.value = true
        viewModelScope.launch {
            try {
                respository.getMovieCreditsFlow(id).collect { response ->
                    if (response.isSuccessful) {
                        response.body()?.let {
                            loading.value = false
                            credits.value = it
                        }
                    }
                }
            } catch (e: Exception) {
                loading.value = false
                error.value = e.localizedMessage
            }
        }
    }

    fun getComments(id: Int ) {
        loading.value = true
        viewModelScope.launch {
            try {
                respository.getCommentsFlow(id).collect { response ->
                    if (response.isSuccessful) {
                        response.body()?.results?.let {
                            loading.value = false
                            comments.value = it as List<Review>
                        }
                    }
                }
            } catch (e: Exception) {
                loading.value = false
                error.value = e.localizedMessage
            }
        }
    }



    fun getMovieTrailers(id: Int) {
        loading.value = true
        viewModelScope.launch {
            try {
                respository.getTrailerFlow(id).collect { response ->
                    if (response.results?.isNotEmpty() ?: false) {
                        trailer.value = response.results as List<Video>
                    }
                }
            } catch (e: Exception) {
                error.value = e.localizedMessage
            }
        }
    }

    fun getmore() {
        loading.value = true
        viewModelScope.launch {
            try {
                respository.getPopularMoviesFlow().collect { response ->
                    if (response.isSuccessful) {
                        response.body()?.results?.let {
                            loading.value = false

                            more.value = it
                        }
                    }
                }
            } catch (
                e: Exception
            ) {
                loading.value = false
                error.value = e.localizedMessage
            }

        }
    }

    suspend fun isMovieAdded(id: Int): Boolean {
return respository.isMovieAdded(id)
    }


    fun addMovie(movie: MyListModel) {
        viewModelScope.launch {
            respository.addMovie(movie)
        }

    }
    fun deleteMovie(id: Int) {
        viewModelScope.launch {
            respository.deleteMovie(id)
        }
    }



    suspend fun addDownload(download: com.example.movamovieapp.model.DownloadModel) {
        respository.addDownload(download)
    }

    suspend fun isDownloadAdded(id: Int): Boolean {
        return respository.isDownloadAdded(id)
        }



}
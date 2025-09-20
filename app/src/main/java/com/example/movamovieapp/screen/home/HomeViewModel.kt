package com.example.movamovieapp.screen.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movamovieapp.di.MovieRepository
import com.example.movamovieapp.model.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()

    val popularMovies = MutableLiveData<MovieState>()
    val top10Movies = MutableLiveData<MovieState>()
    val upcomingMovies = MutableLiveData<MovieState>()
    val nowPlayingMovies = MutableLiveData<MovieState>()
    private val _featuredMovie = MutableLiveData<Result?>()
    val featuredMovie: MutableLiveData<Result?> get() = _featuredMovie

    fun getPopularMovies() {
        loading.value = true
        viewModelScope.launch {
            try {
                movieRepository.getPopularMoviesFlow().collect { response ->
                    if (response.isSuccessful) {
                        response.body()?.let {
                            popularMovies.value = MovieState.Success(it.results.take(5))
                        }
                        loading.value = false
                    } else {
                        popularMovies.value = MovieState.Error(response.message())
                    }
                }
            } catch (e: Exception) {
                popularMovies.value = MovieState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }

    fun getTopRatedMovies() {
        loading.value = true
        viewModelScope.launch {
            try {
                movieRepository.getTopRatedMoviesFlow().collect { response ->
                    if (response.isSuccessful) {
                        response.body()?.let {
                            top10Movies.value = MovieState.Success(it.results.take(5))
                        }
                        loading.value = false
                    } else {
                        top10Movies.value = MovieState.Error(response.message())
                    }
                }
            } catch (e: Exception) {
                top10Movies.value = MovieState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }

    fun getUpcomingMovies() {
        loading.value = true
        viewModelScope.launch {
            try {
                movieRepository.getUpcomingMoviesFlow().collect { response ->
                    if (response.isSuccessful) {
                        response.body()?.let {
                            upcomingMovies.value = MovieState.Success(it.results.take(5))
                        }
                        loading.value = false
                    } else {
                        upcomingMovies.value = MovieState.Error(response.message())
                    }
                }
            } catch (e: Exception) {
                upcomingMovies.value = MovieState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }

    fun getNowPlayingMovies() {
        loading.value = true
        viewModelScope.launch {
            try {
                movieRepository.getNowPlayingMoviesFlow().collect { response ->
                    if (response.isSuccessful) {
                        response.body()?.let {
                            nowPlayingMovies.value = MovieState.Success(it.results.take(5))
                        }
                        loading.value = false
                    } else {
                        nowPlayingMovies.value = MovieState.Error(response.message())
                    }
                }
            } catch (e: Exception) {
                nowPlayingMovies.value = MovieState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }
    fun loadFeaturedMovie() {
        viewModelScope.launch {
            movieRepository.getPopularMoviesFlow().collect { response ->
                if (response.isSuccessful) {
                    response.body()?.results?.let {
                        val first = it.firstOrNull()
                        _featuredMovie.value = first
                         popularMovies.value = MovieState.Success(it)
                    }
                }
            }
        }
    }

}

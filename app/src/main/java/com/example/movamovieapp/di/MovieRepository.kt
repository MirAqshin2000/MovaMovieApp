package com.example.movamovieapp.di

import com.example.movamovieapp.api.ApiService
import com.example.movamovieapp.model.MovaListModel
import com.example.movamovieapp.model.ReviewResponse
import com.example.movamovieapp.model.TrailerResponse
import com.example.movamovieapp.model.detail.DetailResponse
import com.example.movamovieapp.model.detail.credits.CreditResponse
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class MovieRepository@Inject constructor(
     val apiService: ApiService)

{

    fun getPopularMoviesFlow() : Flow<Response<MovaListModel>> = flow {
        emit(apiService.getPopularMovies())

    }

    fun getTopRatedMoviesFlow() : Flow<Response<MovaListModel>> = flow {
        emit(apiService.getTopRatedMovies())
    }

    fun getUpcomingMoviesFlow() : Flow<Response<MovaListModel>> = flow {
        emit(apiService.getUpcomingMovies())
    }


    fun getNowPlayingMoviesFlow() : Flow<Response<MovaListModel>> = flow {
        emit(apiService.getNowPlayingMovies())
    }


    fun getMovieDetailFlow(movieId: Int) : Flow<Response<DetailResponse>> = flow {
        emit(apiService.getMovieDetail(movieId))
    }

    fun getMovieCreditsFlow(movieId: Int) : Flow<Response<CreditResponse>> = flow {
        emit(apiService.getMovieCredits(movieId))
    }

    fun getCommentsFlow(movieId: Int) : Flow<Response<ReviewResponse>> = flow {
        emit(apiService.getMovieReviews(movieId))
    }
    fun getMoreFlow(movieId: Int) : Flow<Response<ReviewResponse>> = flow {
        emit(apiService.getMovieReviews(movieId))
    }
    fun getTrailerFlow(movieId: Int) : Flow<TrailerResponse> = flow {
        emit(apiService.getMovieTrailers(movieId=movieId))
    }
    fun searchMovies(query: String): Flow<Response<MovaListModel>> = flow {
        emit(apiService.searchMovies(query))
    }


    }

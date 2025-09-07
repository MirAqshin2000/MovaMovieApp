package com.example.movamovieapp.di

import com.example.movamovieapp.api.ApiService
import com.example.movamovieapp.local.MovieDao
import com.example.movamovieapp.local.MovieDatabase
import com.example.movamovieapp.model.MovaListModel
import com.example.movamovieapp.model.MyListModel
import com.example.movamovieapp.model.ReviewResponse
import com.example.movamovieapp.model.TrailerResponse
import com.example.movamovieapp.model.detail.DetailResponse
import com.example.movamovieapp.model.detail.credits.CreditResponse
import com.example.movamovieapp.model.video.VideoResponse
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

class MovieRepository @Inject constructor(
    val apiService: ApiService,
    private val dao: MovieDao
) {

    fun getPopularMoviesFlow(): Flow<Response<MovaListModel>> = flow {
        emit(apiService.getPopularMovies())

    }.flowOn(Dispatchers.IO)

    fun getTopRatedMoviesFlow(): Flow<Response<MovaListModel>> = flow {
        emit(apiService.getTopRatedMovies())
    }.flowOn(Dispatchers.IO)

    fun getUpcomingMoviesFlow(): Flow<Response<MovaListModel>> = flow {
        emit(apiService.getUpcomingMovies())
    }.flowOn(Dispatchers.IO)


    fun getNowPlayingMoviesFlow(): Flow<Response<MovaListModel>> = flow {
        emit(apiService.getNowPlayingMovies())
    }.flowOn(Dispatchers.IO)


    fun getMovieDetailFlow(movieId: Int): Flow<Response<DetailResponse>> = flow {
        emit(apiService.getMovieDetail(movieId))
    }

    fun getMovieCreditsFlow(movieId: Int): Flow<Response<CreditResponse>> = flow {
        emit(apiService.getMovieCredits(movieId))
    }.flowOn(Dispatchers.IO)


    fun getCommentsFlow(movieId: Int): Flow<Response<ReviewResponse>> = flow {
        emit(apiService.getMovieReviews(movieId))
    }.flowOn(Dispatchers.IO)


    fun getMoreFlow(movieId: Int): Flow<Response<ReviewResponse>> = flow {
        emit(apiService.getMovieReviews(movieId))
    }

    fun getTrailerFlow(movieId: Int): Flow<VideoResponse> = flow {
        emit(apiService.getMovieTrailers(movieId = movieId))
    }.flowOn(Dispatchers.IO)

    fun searchMovies(query: String): Flow<Response<MovaListModel>> = flow {
        emit(apiService.searchMovies(query))
    }.flowOn(Dispatchers.IO)



     fun addMovie(movie: MyListModel) {
       return dao.addMovie(movie)
    }
     fun deleteMovie(id: Int) {
        return dao.deleteMovie(id)
    }
     fun getAllMovies(): List<MyListModel> {
        return dao.getAllMovies()
    }


}

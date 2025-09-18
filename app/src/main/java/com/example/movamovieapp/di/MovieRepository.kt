package com.example.movamovieapp.di

import com.example.movamovieapp.api.ApiService
import com.example.movamovieapp.local.CardDao
import com.example.movamovieapp.local.DownloadDao
import com.example.movamovieapp.local.MovieDao
import com.example.movamovieapp.local.MovieDatabase
import com.example.movamovieapp.model.CardModel
import com.example.movamovieapp.model.DownloadModel
import com.example.movamovieapp.model.MovaListModel
import com.example.movamovieapp.model.MyListModel
import com.example.movamovieapp.model.Result
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
    private val dao: MovieDao,
    private val cardDao: CardDao,
private val downloadDao: DownloadDao
)

{

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



     suspend fun addMovie(movie: MyListModel) {
       return dao.addMovie(movie)
    }
    suspend fun deleteMovie(id: Int): Int {
        return dao.deleteMovie(id)
    }
     suspend fun getAllMovies(): List<MyListModel> {
        return dao.getAllMovies()
    }
    suspend fun isMovieAdded(id: Int): Boolean {
        return dao.isMovieInList(id)
    }



     fun getCards(): Flow<List<com.example.movamovieapp.model.CardModel>> {
        return cardDao.getAllCards()

    }
    suspend fun addCard(card: CardModel){
        cardDao.addCard(card)
    }
    suspend fun deleteCard(id: Int): Int {
        return cardDao.deleteCard(id)
    }




     fun getDownloadMovies(): Flow<List<com.example.movamovieapp.model.DownloadModel>> {
        return downloadDao.getAllDownloads()

    }

    suspend fun addDownload(download: com.example.movamovieapp.model.DownloadModel) {
        downloadDao.addDownload(download)
    }

    suspend fun deleteDownload(id: Int) {
       return downloadDao.deleteDownload(id)
    }

    suspend fun isDownloadAdded(id: Int): Boolean {
        return downloadDao.isDownloadInList(id)
    }

}

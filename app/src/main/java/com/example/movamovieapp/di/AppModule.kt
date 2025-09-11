package com.example.movamovieapp.di

import android.content.Context
import androidx.room.Room
import com.example.movamovieapp.api.ApiService
import com.example.movamovieapp.local.CardDao
import com.example.movamovieapp.local.MovieDao
import com.example.movamovieapp.local.MovieDatabase
import com.example.movamovieapp.util.Constants.BASE_URL
import com.example.movamovieapp.util.SharedPrefManager
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

@Singleton
@Provides
fun providefireBaseAuth() :FirebaseAuth{
    return FirebaseAuth.getInstance()
}

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

//@Singleton
//@Provides
//fun provideMovieRepository(apiService: ApiService): MovieRepository {
//    return MovieRepository( apiService)
//}
    @Singleton
    @Provides
    fun provideSharedPrefManager(@ApplicationContext context: Context): SharedPrefManager {
        return SharedPrefManager(context)
    }

@Singleton
@Provides
fun provideRoom(@ApplicationContext context: Context): MovieDatabase {
    return Room.databaseBuilder(context, MovieDatabase::class.java, "movie_database")
        .build()
}
    @Singleton
    @Provides
    fun MovieDao(movieDatabase: MovieDatabase): MovieDao {
        return movieDatabase.createMovieDao
    }
    @Provides
    fun provideCardDao(movieDatabase: MovieDatabase): CardDao {
        return movieDatabase.createCardDao
    }




}




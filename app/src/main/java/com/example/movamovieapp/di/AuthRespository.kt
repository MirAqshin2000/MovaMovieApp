package com.example.movamovieapp.di

import com.example.movamovieapp.api.NetworkResource
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import java.net.UnknownHostException
import javax.inject.Inject

class AuthRespository @Inject constructor(

    private val firebaseAuth: FirebaseAuth,
) {


    fun login(email: String, password: String): Flow<NetworkResource<AuthResult>> =
        flow {
            emit(NetworkResource.Loading())
            try {
                val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
                if (!result.user?.email.isNullOrEmpty()) {
                    emit(NetworkResource.Success(result))
                } else {
                    emit(NetworkResource.Error("Email or password is incorrect"))
                }
            } catch (e: Exception) {

                val errorMessage = when (e) {
                    is FirebaseNetworkException, is UnknownHostException -> "No internet connection"
                    is IllegalArgumentException,
                    is com.google.firebase.auth.FirebaseAuthInvalidCredentialsException -> "Invalid email or password"
                    is com.google.firebase.auth.FirebaseAuthInvalidUserException -> "User not found"
                    else -> e.localizedMessage ?: "An unexpected error occurred"
                }

                emit(NetworkResource.Error(errorMessage))
            }
        }.flowOn(Dispatchers.IO)

    fun register(email: String, password: String): Flow<NetworkResource<AuthResult>> =
        flow {
            emit(NetworkResource.Loading())

            try {


                val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
                if (!result.user?.email.isNullOrEmpty()) {
                    emit(NetworkResource.Success(result))
                } else {
                    emit(NetworkResource.Error("Email or password is incorrect"))
                }
            } catch (e: Exception) {
                val errorMessage = when (e) {
                    is FirebaseNetworkException, is UnknownHostException -> "No internet connection"
                    is IllegalArgumentException -> "Invalid email or password"
                    else -> e.localizedMessage ?: "An unexpected error occurred"
                }
                emit(NetworkResource.Error(errorMessage))
            }
        }.flowOn(Dispatchers.IO)
}
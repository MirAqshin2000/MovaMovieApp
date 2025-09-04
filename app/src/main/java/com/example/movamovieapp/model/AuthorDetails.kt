package com.example.movamovieapp.model


import com.google.gson.annotations.SerializedName

data class AuthorDetails(
    @SerializedName("avatar_path")
    val avatarPath: String? = "",
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("rating")
    val rating: Int? = 0,
    @SerializedName("username")
    val username: String? = ""
)
package com.example.movamovieapp.model


import com.google.gson.annotations.SerializedName

data class ResultX(
    @SerializedName("author")
    val author: String? = "",
    @SerializedName("author_details")
    val authorDetails: AuthorDetails? = AuthorDetails(),
    @SerializedName("content")
    val content: String? = "",
    @SerializedName("created_at")
    val createdAt: String? = "",
    @SerializedName("id")
    val id: String? = "",
    @SerializedName("updated_at")
    val updatedAt: String? = "",
    @SerializedName("url")
    val url: String? = ""
)
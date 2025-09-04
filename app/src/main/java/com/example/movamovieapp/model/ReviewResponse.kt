package com.example.movamovieapp.model


import com.google.gson.annotations.SerializedName

data class ReviewResponse(
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("page")
    val page: Int? = 0,
    @SerializedName("results")
    val results: List<Result>? = listOf(),
    @SerializedName("total_pages")
    val totalPages: Int? = 0,
    @SerializedName("total_results")
    val totalResults: Int? = 0
)
package com.example.movamovieapp.model.detail.credits


import com.google.gson.annotations.SerializedName

data class CreditResponse(
    @SerializedName("cast")
    val cast: List<Cast> = listOf(),
    @SerializedName("crew")
    val crew: List<Crew?>? = listOf(),
    @SerializedName("id")
    val id: Int? = 0
)
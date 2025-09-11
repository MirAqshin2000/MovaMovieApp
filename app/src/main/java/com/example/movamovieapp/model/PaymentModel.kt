package com.example.movamovieapp.model

import androidx.annotation.DrawableRes

data class PaymentModel (
@DrawableRes
val image: Int,

val title: String,

var selected: Boolean,

    var cardNumber: String = "",

)

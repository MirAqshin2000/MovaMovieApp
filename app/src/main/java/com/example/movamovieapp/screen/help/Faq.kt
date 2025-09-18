package com.example.movamovieapp.screen.help

data class Faq(
    val question: String,
    val answer: String,
    var isExpanded: Boolean = false

)

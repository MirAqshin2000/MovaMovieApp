package com.example.movamovieapp.enum1

enum class CategoryMovie (val apiValue: String, val displayName: String) {
    NOW_PLAYING("now_playing", "Now Playing Movies"),
    POPULAR("popular", "Popular Movies"),
    TOP_RATED("top_rated", "Top Rated Movies"),
    UPCOMING("upcoming", "Upcoming Movies");

    companion object {
        fun fromApiValue(value: String): CategoryMovie {
            return values().firstOrNull { it.apiValue == value } ?: POPULAR
        }
    }
}

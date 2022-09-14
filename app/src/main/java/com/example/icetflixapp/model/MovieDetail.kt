package com.example.icetflixapp.model

data class MovieDetail(
    val movie: Movie,
    val similars: List<Movie>
)

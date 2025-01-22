package com.supplieswind.kmpmovies.data

public class MoviesRepository(private val moviesService: MovieService) {

    suspend fun fetchPopularMovies(): List<Movie> {
        return moviesService
            .fetchPopularMovies().results
            .map {
                it.toDomainMovie()
            }
    }

    suspend fun fetchMovieById(id: Int): Movie {
        return moviesService
            .fetchMovieById(id)
            .toDomainMovie()
    }
}

// funcion de extension del RemoteMovie
private fun RemoteMovie.toDomainMovie() = Movie(
    id = id,
    title = title,
    poster = "https://image.tmdb.org/t/p/w500$posterPath"
)

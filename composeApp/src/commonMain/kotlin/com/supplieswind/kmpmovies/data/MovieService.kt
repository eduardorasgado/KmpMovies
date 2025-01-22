package com.supplieswind.kmpmovies.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class MovieService(private val client: HttpClient) {

    suspend fun fetchPopularMovies(): RemoteResult {
        try {
            return client
                .get("discover/movie") {
                    parameter("sort_by", "popularity.desc")
                }
                .body<RemoteResult>()
        } catch (ex: Exception) {
            println("Error occurred while fetching movies from remote: ${ex.message}")
            throw ex
        }
    }

    suspend fun fetchMovieById(id: Int): RemoteMovie {
        try {
            return client
                .get("movie/$id")
                .body<RemoteMovie>()
        } catch (ex: Exception) {
            println("Error occurred while fetching movie with id $id -> ${ex.message}")
            throw ex
        }
    }
}
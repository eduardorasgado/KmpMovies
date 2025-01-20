package com.supplieswind.kmpmovies.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpHeaders

class MovieService(
    private val apiKey: String,
    private val client: HttpClient
) {
    suspend fun fetchPopularMovies(): RemoteResult {
        return client
            .get(
                "https://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc"
            ) {
                url {
                    headers.append(HttpHeaders.Authorization, "Bearer $apiKey")
                }

            }
            .body<RemoteResult>()
    }
}
package com.supplieswind.kmpmovies.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.supplieswind.kmpmovies.data.MovieService
import com.supplieswind.kmpmovies.data.MoviesRepository
import com.supplieswind.kmpmovies.ui.screens.detail.DetailScreen
import com.supplieswind.kmpmovies.ui.screens.detail.DetailViewModel
import com.supplieswind.kmpmovies.ui.screens.home.HomeScreen
import com.supplieswind.kmpmovies.ui.screens.home.HomeViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.URLProtocol
import io.ktor.http.encodedPath
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json
import kmpmovies.composeapp.generated.resources.Res
import kmpmovies.composeapp.generated.resources.api_key
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.stringResource

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val apiKey = stringResource(Res.string.api_key)

    // remember para no estar creando el HttpClient cada vez que se hacen recomposiciones
    val client = remember {
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    // para que no nos lance excepcion si llega algun campo desconocido como response
                    // esto es util cuando no queremos deserializar todo el json que viene como response
                    ignoreUnknownKeys = true
                    isLenient = true
                })
            }

            defaultRequest {
                // https://api.themoviedb.org/3/requestUrlString?api_key=APIKEY
                url {
                    protocol = URLProtocol.HTTPS
                    host = "api.themoviedb.org"
                    path("3/")
                    parameters.append("api_key", apiKey)
                }
            }
        }
    }
    val moviesRepository = MoviesRepository(MovieService(client))
    val viewModel = viewModel {
        HomeViewModel(moviesRepository)
    }

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                onMovieClick = { movie ->
                    navController.navigate("details/${movie.id}")
                },
                vm = viewModel
            )
        }

        composable(
            route = "details/{movieId}",
            arguments = listOf(navArgument("movieId") {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("movieId")
            if (movieId != null) {
                val detailViewModel = viewModel {

                    DetailViewModel(movieId, moviesRepository)

                }
                DetailScreen(
                    onBack = { navController.popBackStack() }, vm = detailViewModel)
            }
        }
    }
}
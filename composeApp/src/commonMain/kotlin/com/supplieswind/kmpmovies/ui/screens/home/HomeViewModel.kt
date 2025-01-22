package com.supplieswind.kmpmovies.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.supplieswind.kmpmovies.data.Movie
import com.supplieswind.kmpmovies.data.MoviesRepository
import com.supplieswind.kmpmovies.data.RemoteMovie
import kotlinx.coroutines.launch

class HomeViewModel(private val moviesRepository: MoviesRepository)
    : ViewModel() {

    // mutableStateOf is a function from the kotlinx.coroutines library. It creates an observable state holder.
    // Whenever the value inside state changes, any UI components observing this state will be automatically updated.
    var state by mutableStateOf(UiState())
        //  restricts the ability to directly modify the state variable from outside the HomeViewModel class.
        //  This ensures that the state is managed and updated only within the ViewModel.
        private set

    init {
        // simulamos una carga de recursos desde api con delay
        viewModelScope.launch {
            state = UiState(loading = true)
            state = UiState(
                loading = false,
                movies = moviesRepository.fetchPopularMovies()
            )
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val movies: List<Movie> = emptyList()
    )
}
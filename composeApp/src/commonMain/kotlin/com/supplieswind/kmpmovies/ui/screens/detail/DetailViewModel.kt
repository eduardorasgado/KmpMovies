package com.supplieswind.kmpmovies.ui.screens.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.supplieswind.kmpmovies.data.Movie
import com.supplieswind.kmpmovies.data.MoviesRepository
import kotlinx.coroutines.launch

class DetailViewModel(
    private val id: Int,
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    var state by mutableStateOf(UiState())
        private set

    init {
        viewModelScope.launch {
            state = UiState(loading = true)
            state = UiState(
                loading = false,
                movie = moviesRepository.fetchMovieById(id)
            )
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val movie: Movie? = null
    )
}
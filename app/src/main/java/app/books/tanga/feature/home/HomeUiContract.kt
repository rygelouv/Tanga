package app.books.tanga.feature.home

data class HomeUiState(
    val isLoading: Boolean = false,
    val userFirstName: String? = null,
    val userPhotoUrl: String? = null
)
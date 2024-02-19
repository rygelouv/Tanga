package app.books.tanga.feature.summary

import app.books.tanga.common.ui.ProgressState
import app.books.tanga.entity.Summary
import app.books.tanga.entity.SummaryId
import app.books.tanga.errors.toUiError
import app.books.tanga.feature.library.FavoriteInteractor
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

interface SummaryBehaviorDelegate {

    val summaryContentState: StateFlow<SummaryContentState>

    fun setUp(scope: CoroutineScope)

    fun loadSummary(summaryId: SummaryId)

    fun toggleFavorite()
}

class SummaryBehaviorDelegateImpl @Inject constructor(
    private val summaryInteractor: SummaryInteractor,
    private val favoriteInteractor: FavoriteInteractor,

) : SummaryBehaviorDelegate {

    private var coroutineScope: CoroutineScope? = null

    private val _summaryContentState: MutableStateFlow<SummaryContentState> = MutableStateFlow(SummaryContentState())
    override val summaryContentState: StateFlow<SummaryContentState>
        get() = _summaryContentState.asStateFlow()

    /**
     * Holding the domain summary object to help perform future operations
     */
    private lateinit var summary: Summary

    override fun setUp(scope: CoroutineScope) {
        coroutineScope = scope
    }

    override fun loadSummary(summaryId: SummaryId) {
        coroutineScope?.launch {
            summaryInteractor
                .getSummary(summaryId)
                .onSuccess {
                    summary = it
                    _summaryContentState.update { state ->
                        state.copy(
                            summary = summary.toSummaryUi()
                        )
                    }
                    loadFavoriteStatus(summaryId)
                }.onFailure {
                    Timber.e(it, "Error loading summary with id: $summaryId")
                    _summaryContentState.update { state ->
                        state.copy(
                            error = it.toUiError()
                        )
                    }
                }
        }
    }

    /**
     * Load the favorite status for the given summary id
     */
    private suspend fun loadFavoriteStatus(summaryId: SummaryId) {
        favoriteInteractor
            .isFavorite(summaryId)
            .onSuccess { isFavorite ->
                _summaryContentState.update { it.copy(isFavorite = isFavorite) }
            }.onFailure { error ->
                Timber.e(error, "Error loading favorite status")
            }
    }

    override fun toggleFavorite() {
        // Do nothing if the summary is not initialized
        if (this::summary.isInitialized.not()) return

        // Show saving progress
        _summaryContentState.update { it.copy(favoriteProgressState = ProgressState.Show) }

        // Get the current favorite status
        val isFavorite = _summaryContentState.value.isFavorite

        coroutineScope?.launch {
            if (isFavorite) {
                removeFavorite()
            } else {
                saveFavorite()
            }
        }
    }

    private suspend fun saveFavorite() {
        favoriteInteractor
            .createFavorite(summary)
            .onSuccess {
                _summaryContentState.update {
                    it.copy(
                        isFavorite = true,
                        favoriteProgressState = ProgressState.Hide
                    )
                }
                // Show a snackbar to notify the user that the summary is added to favorites
            }.onFailure { error ->
                _summaryContentState.update { it.copy(favoriteProgressState = ProgressState.Hide) }
                Timber.e(error, "Error creating favorite")
            }
    }

    private suspend fun removeFavorite() {
        favoriteInteractor
            .deleteFavoriteBySummaryId(summary.id)
            .onSuccess {
                _summaryContentState.update {
                    it.copy(
                        isFavorite = false,
                        favoriteProgressState = ProgressState.Hide
                    )
                }
                // Show a snackbar to notify the user that the summary is removed from favorites
            }.onFailure { error ->
                _summaryContentState.update { it.copy(favoriteProgressState = ProgressState.Hide) }
                Timber.e(error, "Error deleting favorite")
            }
    }
}

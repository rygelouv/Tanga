package app.books.tanga.feature.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.books.tanga.data.preferences.DefaultPrefDataStoreRepository
import app.books.tanga.tracking.AnalyticsTracker
import app.books.tanga.tracking.Events
import app.books.tanga.tracking.Page
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val repository: DefaultPrefDataStoreRepository,
    private val analyticsTracker: AnalyticsTracker
) : ViewModel() {

    fun onPageStarted(page: Page) {
        analyticsTracker.trackPage(page)
    }

    fun onOnboardingStarted() {
        viewModelScope.launch {
            analyticsTracker.track(Events.TAP_ONBOARDING_GET_STARTED)
        }
    }

    fun onOnboardingCompleted() {
        viewModelScope.launch {
            analyticsTracker.track(Events.TAP_FINISH_ONBOARDING)
            repository.saveOnboardingCompletionState(true)
        }
    }
}

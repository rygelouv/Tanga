package app.books.tanga.feature.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.books.tanga.data.preferences.DefaultPrefDataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val repository: DefaultPrefDataStoreRepository
) : ViewModel() {

    fun onOnboardingCompleted() {
        viewModelScope.launch {
            repository.saveOnboardingCompletionState(true)
        }
    }
}
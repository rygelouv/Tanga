package app.books.tanga.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import app.books.tanga.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

val Context.defaultDataStore: DataStore<Preferences>
        by preferencesDataStore(name = "default_tanga_shared_prefs")

class DefaultPrefDataStoreRepository @Inject constructor(
    context: Context,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {

    private object PreferencesKey {
        val onBoardingCompletionKey = booleanPreferencesKey(name = "onboarding_completed")
    }

    private val dataStore = context.defaultDataStore

    suspend fun saveOnboardingCompletionState(completed: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.onBoardingCompletionKey] = completed
        }
    }

    suspend fun getOnboardingCompletionState(): Boolean {
        return withContext(dispatcher) {
            dataStore.data.map { preferences ->
                preferences[PreferencesKey.onBoardingCompletionKey] ?: false
            }.first()
        }
    }
}
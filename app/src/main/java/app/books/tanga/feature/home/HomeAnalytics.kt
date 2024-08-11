package app.books.tanga.feature.home

import app.books.tanga.data.preferences.DefaultPrefDataStoreRepository
import app.books.tanga.entity.CategoryId
import app.books.tanga.entity.SummaryId
import app.books.tanga.tracking.AnalyticsTracker
import app.books.tanga.tracking.Events
import app.books.tanga.tracking.Pages
import app.books.tanga.tracking.Properties
import app.books.tanga.tracking.Property
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject
import kotlinx.coroutines.flow.first

/**
 * Helper class to track events related to the Home screen.
 * This is to help declutter [HomeViewModel] from tracking logic and to avoid adding too many functions
 */
interface HomeAnalytics {
    fun trackHomePage()

    fun onProfilePictureClick()

    fun onSearchClick()

    suspend fun onSummaryClick(summaryId: SummaryId)

    fun onSeeAllClick(categoryId: CategoryId)

    fun onWeeklySummaryClick()
}

@ViewModelScoped
class HomeAnalyticsImpl @Inject constructor(
    private val analyticsTracker: AnalyticsTracker,
    private val preferencesRepository: DefaultPrefDataStoreRepository
) : HomeAnalytics {
    // This could be blocking the UI thread, but it's fine for now we will deal with it later
    private val weeklySummaryFlow by lazy { preferencesRepository.getWeeklySummary() }

    override fun trackHomePage() {
        analyticsTracker.trackPage(Pages.HOME)
    }

    override fun onProfilePictureClick() {
        analyticsTracker.track(Events.TAP_PROFILE_PICTURE)
    }

    override fun onSearchClick() {
        analyticsTracker.track(Events.TAP_SEARCH)
    }

    override suspend fun onSummaryClick(summaryId: SummaryId) {
        val weeklySummaryId = weeklySummaryFlow.first()
        val properties = mapOf<Property, Any>(Properties.SUMMARY_ID to summaryId.value)
        if (summaryId == weeklySummaryId) {
            analyticsTracker.track(Events.TAP_WEEKLY_SUMMARY, properties)
            return
        }
        analyticsTracker.track(Events.TAP_SUMMARY, properties)
    }

    override fun onSeeAllClick(categoryId: CategoryId) {
        analyticsTracker.track(Events.TAP_SEE_ALL_BOOKS_IN_CATEGORY, mapOf(Properties.CATEGORY_ID to categoryId.value))
    }

    override fun onWeeklySummaryClick() {
        analyticsTracker.track(Events.TAP_WEEKLY_SUMMARY)
    }
}

@Module
@InstallIn(ViewModelComponent::class)
interface HomeAnalyticsModule {

    @ViewModelScoped
    @Binds
    fun bindHomeAnalytics(impl: HomeAnalyticsImpl): HomeAnalytics
}

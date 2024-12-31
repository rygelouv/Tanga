package app.books.tanga.feature.summary.details

import app.books.tanga.entity.SummaryId
import app.books.tanga.tracking.AnalyticsTracker
import app.books.tanga.tracking.Events
import app.books.tanga.tracking.Pages
import app.books.tanga.tracking.Properties
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

interface SummaryDetailsAnalytics {

    fun trackPage()

    fun onShare(summaryId: SummaryId)

    fun onRecommendationClick(summaryId: SummaryId)

    fun onToggleFavoriteClick(summaryId: SummaryId)

    fun onFavoriteSaved(summaryId: SummaryId)

    fun onFavoriteRemoved(summaryId: SummaryId)
}

@ViewModelScoped
class SummaryDetailsAnalyticsImpl @Inject constructor(
    private val analyticsTracker: AnalyticsTracker
) : SummaryDetailsAnalytics {

    override fun trackPage() {
        analyticsTracker.trackPage(Pages.SUMMARY_DETAILS)
    }

    override fun onShare(summaryId: SummaryId) {
        analyticsTracker.track(Events.TAP_SHARE_SUMMARY, mapOf(Properties.SUMMARY_ID to summaryId.value))
    }

    override fun onRecommendationClick(summaryId: SummaryId) {
        analyticsTracker.track(Events.TAP_SUMMARY, mapOf(Properties.SUMMARY_ID to summaryId.value))
    }

    override fun onToggleFavoriteClick(summaryId: SummaryId) {
        analyticsTracker.track(Events.TAP_SAVE_SUMMARY, mapOf(Properties.SUMMARY_ID to summaryId.value))
    }

    override fun onFavoriteSaved(summaryId: SummaryId) {
        analyticsTracker.track(Events.ACTION_SUMMARY_SAVED, mapOf(Properties.SUMMARY_ID to summaryId.value))
    }

    override fun onFavoriteRemoved(summaryId: SummaryId) {
        analyticsTracker.track(Events.ACTION_SUMMARY_REMOVED, mapOf(Properties.SUMMARY_ID to summaryId.value))
    }
}

@Module
@InstallIn(ViewModelComponent::class)
interface SummaryDetailsAnalyticsModule {

    @ViewModelScoped
    @Binds
    fun bindSummaryDetailsAnalytics(impl: SummaryDetailsAnalyticsImpl): SummaryDetailsAnalytics
}

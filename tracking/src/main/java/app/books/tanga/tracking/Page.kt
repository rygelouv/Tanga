package app.books.tanga.tracking

/**
 * Represents a page that can be tracked in analytics.
 */
sealed interface Page {
    val pageName: String
}

enum class Pages(override val pageName: String) : Page {
    LANDING("landing_screen"),
    ONBOARDING("onboarding_screen"),
    AUTHENTICATION("authentication_screen"),
    HOME("home_screen"),
    LIBRARY("library_screen"),
    PROFILE("profile_screen"),
    SUMMARY_BY_CATEGORY("summaries_by_category_screen"),
    SEARCH("search_screen"),
    SUMMARY_DETAILS("summary_details_screen"),
    PLAY_SUMMARY_AUDIO("play_summary_audio_screen"),
    SUBSCRIPTION("subscription_screen"),
    READ_SUMMARY("read_summary_screen"),
    SETTINGS("settings_screen"),
    PRIVACY_AND_TERMS("privacy_and_terms_screen"),
    DELETE_ACCOUNT("delete_account_screen")
}

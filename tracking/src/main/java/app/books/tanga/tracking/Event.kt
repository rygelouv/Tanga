package app.books.tanga.tracking

/**
 * Represents an analytics event that can be tracked.
 */
sealed interface Event {
    val eventName: String
}

enum class Events(override val eventName: String) : Event {

    // region tap events
    TAP_ONBOARDING_GET_STARTED("android_tap_get_started"),
    TAP_FINISH_ONBOARDING("android_tap_finish_onboarding"),
    TAP_GOOGLE_SIGN_IN("android_tap_google_sign_in"),
    TAP_SKIP_SIGN_IN("android_tap_skip_sign_in"),
    TAP_AUTH_PRIVACY_AND_TERMS("android_auth_tap_privacy_and_terms"),
    TAP_SEARCH("android_tap_search"),
    TAP_SUMMARY("android_tap_summary"), // Needs properties
    TAP_PROFILE_PICTURE("android_tap_profile_picture"),
    TAP_WEEKLY_SUMMARY("android_tap_weekly_summary"), // Needs properties
    TAP_SEE_ALL_BOOKS_IN_CATEGORY("android_tap_see_all_books_in_category"), // Needs properties

    // TAP_EXPLORE_MORE_SUMMARIES_BY_CATEGORY("android_tap_explore_more_by_category"), Not tracking for now
    TAP_EXPLORE_SUMMARIES_FROM_EMPTY_LIBRARY("android_tap_explore_from_empty_library"),
    TAP_CATEGORY_ITEM_IN_SEARCH("android_tap_category_item_in_search"), // Needs properties

    // TAP_PLAY_SUMMARY_AUDIO("android_tap_play_summary_audio"), Not tracking for now
    // TAP_READ_SUMMARY("android_tap_read_summary"), Not tracking for now
    // TAP_VISUALIZE_SUMMARY_GRAPHIC("android_tap_visualize_summary_graphic"), Not tracking for now
    TAP_SAVE_SUMMARY("android_tap_save_summary"), // Needs properties
    TAP_SHARE_SUMMARY("android_tap_share_summary"), // Needs properties
    TAP_PURCHASE_BOOK("android_tap_purchase_book"), // Needs properties
    TAP_REMOVE_SAVED_SUMMARY("android_tap_remove_saved_summary"), // Needs properties
    TAP_PLAY_START_AUDIO("android_tap_play_start_audio"),
    TAP_TANGA_PREMIUM_UPGRADE("android_tap_tanga_premium_upgrade"),
    TAP_PROFILE_CONTACT_US("android_tap_profile_contact_us"),
    TAP_PROFILE_SETTINGS("android_tap_profile_settings"),
    TAP_PROFILE_PRIVACY_AND_TERMS("android_tap_profile_privacy_and_terms"),
    TAP_PROFILE_LOG_OUT("android_tap_profile_log_out"),
    TAP_PROFILE_DELETE_ACCOUNT("android_tap_profile_delete_account"),
    TAP_YEARLY_SUBSCRIPTION("android_tap_yearly_subscription"), // Needs properties
    TAP_MONTHLY_SUBSCRIPTION("android_tap_monthly_subscription"), // Needs properties
    TAP_PRIVACY_POLICY("android_tap_privacy_policy"),
    TAP_TERMS_OF_SERVICE("android_tap_terms_of_service"),
    // endregion

    // region performed action / operation events
    ACTION_USER_SIGNED_IN("android_action_user_signed_in"),
    ACTION_USER_SIGNED_OUT("android_action_user_signed_out"),
    ACTION_ACCOUNT_DELETED("android_action_account_deleted"),
    ACTION_SUMMARY_SAVED("android_action_summary_saved"),
    ACTION_SUMMARY_REMOVED("android_action_summary_removed"),
    ACTION_SEARCH("android_action_search"),
    ACTION_SUBSCRIPTION_PURCHASED("android_action_subscription_purchased"),
    ACTION_SUMMARY_AUDIO_FINISHED_PLAYING("android_action_audio_finished_playing"),
    // endregion
}

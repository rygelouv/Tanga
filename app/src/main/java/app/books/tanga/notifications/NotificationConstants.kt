package app.books.tanga.notifications

object NotificationConstants {
    const val SUMMARY_ID = "summaryId"
    const val TITLE = "title"
    const val AUTHOR = "author"
    const val COVER_IMAGE_URL = "coverImageUrl"
    const val DEEP_LINK = "deepLink"
    const val TYPE = "type"

    const val NOTIFICATION_ID_NUMBER_MIN = 10
    const val NOTIFICATION_ID_NUMBER_MAX = 200
}

object Topics {
    const val NEW_SUMMARIES = "new_summaries"
    const val WEEKLY_SUMMARY = "weekly_summary"
    const val SUBSCRIPTIONS = "subscriptions"
}

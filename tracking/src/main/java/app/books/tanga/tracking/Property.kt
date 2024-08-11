package app.books.tanga.tracking

/**
 * Represents a property that can be attached to an analytics event or page.
 */
sealed interface Property {
    val propertyName: String
}

enum class Properties(override val propertyName: String) : Property {
    SUMMARY_ID("summary_id"),
    CATEGORY_ID("category_id"),
    CATEGORY_NAME("category_name"),
    SEARCH_QUERY("search_query"),
    SUBSCRIPTION_TYPE("subscription_type"),
    SUBSCRIPTION_PRICE("subscription_price"),
}

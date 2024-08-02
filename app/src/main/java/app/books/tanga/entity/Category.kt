package app.books.tanga.entity

import app.books.tanga.coreui.R.*

@JvmInline
value class CategoryId(val value: String)

/**
 * Class representing a Category
 */
data class Category(
    val id: CategoryId,
    val name: String
)

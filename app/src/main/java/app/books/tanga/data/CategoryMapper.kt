package app.books.tanga.data

import app.books.tanga.domain.categories.Category

fun FirestoreData.toCategory(): Category {
    return Category(
        name = this[FirestoreDatabase.Categories.Fields.NAME].toString(),
        id = this[FirestoreDatabase.Categories.Fields.SLUG].toString()
    )
}
package app.books.tanga.data.category

import app.books.tanga.data.FirestoreData
import app.books.tanga.data.FirestoreDatabase
import app.books.tanga.entity.Category
import app.books.tanga.entity.CategoryId

fun FirestoreData.toCategory(): Category {
    return Category(
        name = this[FirestoreDatabase.Categories.Fields.NAME].toString(),
        id = CategoryId(this[FirestoreDatabase.Categories.Fields.SLUG].toString())
    )
}
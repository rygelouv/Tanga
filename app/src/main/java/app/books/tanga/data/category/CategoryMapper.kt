package app.books.tanga.data.category

import app.books.tanga.firestore.FirestoreDatabase
import app.books.tanga.entity.Category
import app.books.tanga.entity.CategoryId
import app.books.tanga.firestore.FirestoreData

fun FirestoreData.toCategory(): Category {
    return Category(
        name = this[FirestoreDatabase.Categories.Fields.NAME].toString(),
        id = CategoryId(this[FirestoreDatabase.Categories.Fields.SLUG].toString())
    )
}
package app.books.tanga.data.summary

import app.books.tanga.entity.CategoryId
import app.books.tanga.entity.Summary
import app.books.tanga.entity.SummaryId
import app.books.tanga.firestore.FirestoreData
import app.books.tanga.firestore.FirestoreDatabase
import timber.log.Timber

private fun Any?.toCategoryIds(): List<CategoryId> =
    (this as? List<*>)?.map { CategoryId(it.toString()) }
        ?: run { emptyList<CategoryId>() }.also { Timber.e("Error parsing categories") }

fun FirestoreData.toSummary(): Summary = Summary(
    id = SummaryId(this[FirestoreDatabase.Summaries.Fields.SLUG].toString()),
    title = this[FirestoreDatabase.Summaries.Fields.TITLE].toString(),
    author = this[FirestoreDatabase.Summaries.Fields.AUTHOR].toString(),
    synopsis = this[FirestoreDatabase.Summaries.Fields.SYNOPSIS].toString(),
    categories = this[FirestoreDatabase.Summaries.Fields.CATEGORIES].toCategoryIds(),
    coverImageUrl = this[FirestoreDatabase.Summaries.Fields.COVER_IMAGE_URL].toString(),
    playingLength = this[FirestoreDatabase.Summaries.Fields.PLAYING_LENGTH].toString(),
    purchaseBookUrl = this[FirestoreDatabase.Summaries.Fields.PURCHASE_BOOK_URL].toString()
)

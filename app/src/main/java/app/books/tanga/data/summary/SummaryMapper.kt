package app.books.tanga.data.summary

import app.books.tanga.firestore.FirestoreDatabase
import app.books.tanga.entity.CategoryId
import app.books.tanga.entity.Summary
import app.books.tanga.entity.SummaryId
import app.books.tanga.firestore.FirestoreData


private fun Any?.toCategoryIds(): List<CategoryId> {
    return (this as List<String>).map { CategoryId(it) }
}

fun FirestoreData.toSummary(): Summary {
    // TODO: 2023-09-14 Wrap this in a try catch block, track error and return null
    return Summary(
        id = SummaryId(this[FirestoreDatabase.Summaries.Fields.SLUG].toString()),
        title = this[FirestoreDatabase.Summaries.Fields.TITLE].toString(),
        author = this[FirestoreDatabase.Summaries.Fields.AUTHOR].toString(),
        synopsis = this[FirestoreDatabase.Summaries.Fields.SYNOPSIS].toString(),
        categories = this[FirestoreDatabase.Summaries.Fields.CATEGORIES].toCategoryIds(),
        coverImageUrl = this[FirestoreDatabase.Summaries.Fields.COVER_IMAGE_URL].toString(),
        audioUrl = this[FirestoreDatabase.Summaries.Fields.AUDIO_URL].toString(),
        graphicUrl = this[FirestoreDatabase.Summaries.Fields.GRAPHIC_URL].toString(),
        playingLength = this[FirestoreDatabase.Summaries.Fields.PLAYING_LENGTH].toString(),
        textUrl = this[FirestoreDatabase.Summaries.Fields.SUMMARY_URL].toString(),
        videoUrl = this[FirestoreDatabase.Summaries.Fields.VIDEO_URL].toString(),
        authorPictureUrl = this[FirestoreDatabase.Summaries.Fields.AUTHOR_PICTURE_URL].toString(),
        purchaseBookUrl = this[FirestoreDatabase.Summaries.Fields.PURCHASE_BOOK_URL].toString()
    )
}
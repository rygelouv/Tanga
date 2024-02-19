package app.books.tanga.data.download

import app.books.tanga.entity.SummaryId
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

fun FirebaseStorage.summaryReference(summaryId: SummaryId): StorageReference {
    val storageRef = reference

    return storageRef.child(summaryId.value)
}

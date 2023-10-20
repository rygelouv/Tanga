package app.books.tanga.fakes

import app.books.tanga.firestore.FirestoreOperationHandler

class FakeFirestoreOperationHandler : FirestoreOperationHandler {
    override suspend fun <T> executeOperation(operation: suspend () -> T): Result<T> = Result.success(operation())
}

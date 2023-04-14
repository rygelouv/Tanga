package app.books.tanga.data

typealias FirestoreData = Map<String, Any?>

object FirestoreDatabase {
    object Users {
        //Collection Reference
        const val COLLECTION_NAME = "users"

        //Fields
        object Fields {
            const val UID = "uid"
            const val FULL_NAME = "fullName"
            const val EMAIL = "email"
            const val PHOTO_URL = "photoUrl"
            const val CREATED_AT = "createdAt"
        }
    }
}
package app.books.tanga.firestore

typealias FirestoreData = Map<String, Any?>

object FirestoreDatabase {
    object Users {
        // Collection Reference
        const val COLLECTION_NAME = "users"

        // Fields
        object Fields {
            const val UID = "uid"
            const val FULL_NAME = "fullName"
            const val EMAIL = "email"
            const val PHOTO_URL = "photoUrl"
            const val CREATED_AT = "createdAt"
            const val SUBSCRIBED_AT = "subscribedAt"
        }
    }

    object Summaries {
        // Collection Reference
        const val COLLECTION_NAME = "summaries"

        // Fields
        object Fields {
            const val SLUG = "slug"
            const val TITLE = "title"
            const val AUTHOR = "author"
            const val SYNOPSIS = "synopsis"
            const val CATEGORIES = "categories"
            const val IS_VISIBLE = "isVisible"
            const val COVER_IMAGE_URL = "coverImageUrl"
            const val PLAYING_LENGTH = "playingLength"
            const val PURCHASE_BOOK_URL = "purchaseBookUrl"
            const val KEY_LEARNINGS = "keyLearnings"
        }
    }

    object Categories {
        // Collection Reference
        const val COLLECTION_NAME = "categories"

        // Fields
        object Fields {
            const val NAME = "name"
            const val SLUG = "slug"
        }
    }

    object Favorites {
        // Collection Reference
        const val COLLECTION_NAME = "favorites"

        // Fields
        object Fields {
            const val UID = "uid"
            const val TITLE = "title"
            const val AUTHOR = "author"
            const val COVER_URL = "coverUrl"
            const val USER_ID = "userId"
            const val SUMMARY_ID = "summaryId"
            const val PLAYING_LENGTH = "playingLength"
        }
    }

    object WeeklySummary {
        // Collection Reference
        const val COLLECTION_NAME = "weeklySummary"

        // Fields
        object Fields {
            const val SLUG = "slug"
        }
    }

    object AIPrompts {
        // Collection Reference
        const val COLLECTION_NAME = "prompts"

        // Fields
        object Fields {
            const val PROMPTS = "prompts"
            const val ID = "id"
            const val TITLE = "title"
            const val DESCRIPTION = "promptDescription"
        }
    }
}

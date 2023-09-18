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

    object Summaries {
        //Collection Reference
        const val COLLECTION_NAME = "summaries"

        //Fields
        object Fields {
            const val AUDIO_URL = "audioUrl"
            const val AUTHOR = "author"
            const val CATEGORIES= "categories"
            const val COVER_IMAGE_URL = "coverImageUrl"
            const val GRAPHIC_URL = "graphicUrl"
            const val PLAYING_LENGTH = "playingLength"
            const val SLUG = "slug"
            const val SUMMARY_URL = "summaryUrl"
            const val SYNOPSIS = "synopsis"
            const val TITLE = "title"
            const val VIDEO_URL = "videoUrl"
        }
    }

    object Categories {
        //Collection Reference
        const val COLLECTION_NAME = "categories"

        //Fields
        object Fields {
            const val NAME = "name"
            const val SLUG = "slug"
        }
    }
}
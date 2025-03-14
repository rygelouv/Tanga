package app.books.tanga.data.aiprompts

import app.books.tanga.entity.AIPrompt
import app.books.tanga.firestore.FirestoreData
import app.books.tanga.firestore.FirestoreDatabase

fun FirestoreData.toAIPrompt(): AIPrompt =
    AIPrompt(
        id = this[FirestoreDatabase.AIPrompts.Fields.ID].toString(),
        title = this[FirestoreDatabase.AIPrompts.Fields.TITLE].toString(),
        description = this[FirestoreDatabase.AIPrompts.Fields.DESCRIPTION].toString()
    )

package app.books.tanga.entity

import java.util.Date

@JvmInline
value class UserId(val value: String)

data class User(
    val id: UserId,
    val fullName: String,
    val email: String,
    val photoUrl: String?,
    val isAnonymous: Boolean = false,
    val createdAt: Date?,
    val subscribedAt: Date? = null
) {
    val firsName: String
        get() = fullName.split(" ").firstOrNull() ?: fullName
}

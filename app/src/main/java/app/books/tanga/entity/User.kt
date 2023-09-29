package app.books.tanga.entity

@JvmInline
value class UserId(val value: String)

data class User(
    val id: UserId,
    val fullName: String,
    val email: String,
    val photoUrl: String?,
    val isPro: Boolean
) {
    val firsName: String
        get() = fullName.split(" ").firstOrNull() ?: fullName
}
package app.books.tanga.entity

data class User(
    val uid: String,
    val fullName: String,
    val email: String,
    val photoUrl: String?,
    val isPro: Boolean
) {
    val firsName: String
        get() = fullName.split(" ").firstOrNull() ?: fullName
}
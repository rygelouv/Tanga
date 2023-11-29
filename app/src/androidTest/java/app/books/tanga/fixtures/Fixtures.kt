package app.books.tanga.fixtures

import app.books.tanga.entity.CategoryId
import app.books.tanga.entity.Favorite
import app.books.tanga.entity.FavoriteId
import app.books.tanga.entity.Summary
import app.books.tanga.entity.SummaryId
import app.books.tanga.entity.User
import app.books.tanga.entity.UserId
import app.books.tanga.firestore.FirestoreDatabase
import com.google.firebase.Timestamp
import java.util.Date

object Fixtures {
    val dummyFavorite1 = Favorite(
        id = FavoriteId("1"),
        title = "Summary1",
        author = "Author1",
        coverUrl = "CoverUrl1",
        userId = "UserId1",
        summaryId = "SummaryId1",
        playingLength = "PlayingLength1"
    )

    val dummyFavorite2 = Favorite(
        id = FavoriteId("2"),
        title = "Summary2",
        author = "Author2",
        coverUrl = "CoverUrl2",
        userId = "UserId2",
        summaryId = "SummaryId2",
        playingLength = "PlayingLength2"
    )

    val dummySummary1 = Summary(
        id = SummaryId("1"),
        title = "Content1",
        author = "Author1",
        synopsis = "Synopsis1",
        coverImageUrl = "http://example.com/image.png",
        textUrl = "TextUrl1",
        categories = listOf(CategoryId("1"), CategoryId("2")),
        playingLength = "12:20",
        audioUrl = "AudioUrl1",
        graphicUrl = "GraphicUrl1",
        videoUrl = "VideoUrl1",
        authorPictureUrl = "AuthorPictureUrl1",
        purchaseBookUrl = "PurchaseBookUrl1",
    )

    val dummySummary2 = Summary(
        id = SummaryId("2"),
        title = "Content2",
        author = "Author2",
        synopsis = "Synopsis2",
        coverImageUrl = "http://example.com/image.png",
        textUrl = "TextUrl2",
        categories = listOf(CategoryId("3"), CategoryId("4")),
        playingLength = "10:36",
        audioUrl = "AudioUrl2",
        graphicUrl = "GraphicUrl2",
        videoUrl = "VideoUrl2",
        authorPictureUrl = "AuthorPictureUrl2",
        purchaseBookUrl = "PurchaseBookUrl2",
    )

    val dummyUser = User(
        id = UserId("123456"),
        fullName = "John Doe",
        email = "john.doe@example.com",
        photoUrl = "https://example.com/johndoe.jpg",
        isPro = true,
        createdAt = Date()
    )

    val dummyUser2 = User(
        id = UserId("someSessionId"),
        fullName = "Mansa Musa",
        email = "some@mail.com",
        photoUrl = "https://someurl.com",
        isPro = false,
        createdAt = Timestamp(1697806449, 0).toDate()
    )

    val dummyUserFirestoreData = mapOf(
        FirestoreDatabase.Users.Fields.FULL_NAME to "Mansa Musa",
        FirestoreDatabase.Users.Fields.EMAIL to "some@mail.com",
        FirestoreDatabase.Users.Fields.PHOTO_URL to "https://someurl.com",
        FirestoreDatabase.Users.Fields.CREATED_AT to Timestamp(1697806449, 0)
    )
}

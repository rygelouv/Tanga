package app.books.tanga.fixtures

import app.books.tanga.entity.Category
import app.books.tanga.entity.CategoryId
import app.books.tanga.entity.Favorite
import app.books.tanga.entity.FavoriteId
import app.books.tanga.entity.Price
import app.books.tanga.entity.Section
import app.books.tanga.entity.SubscriptionPlan
import app.books.tanga.entity.SubscriptionType
import app.books.tanga.entity.Summary
import app.books.tanga.entity.SummaryId
import app.books.tanga.entity.User
import app.books.tanga.entity.UserId
import app.books.tanga.feature.audioplayer.infrastructure.AudioTrack
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
        categories = listOf(CategoryId("1"), CategoryId("2")),
        playingLength = "12:20",
        purchaseBookUrl = "PurchaseBookUrl1",
    )

    val dummySummary2 = Summary(
        id = SummaryId("2"),
        title = "Content2",
        author = "Author2",
        synopsis = "Synopsis2",
        coverImageUrl = "http://example.com/image.png",
        categories = listOf(CategoryId("3"), CategoryId("4")),
        playingLength = "10:36",
        purchaseBookUrl = "PurchaseBookUrl2",
    )

    val dummyUser = User(
        id = UserId("123456"),
        fullName = "John Doe",
        email = "john.doe@example.com",
        photoUrl = "https://example.com/johndoe.jpg",
        createdAt = Date()
    )

    val dummyUser2 = User(
        id = UserId("someSessionId"),
        fullName = "Mansa Musa",
        email = "some@mail.com",
        photoUrl = "https://someurl.com",
        createdAt = Timestamp(1697806449, 0).toDate()
    )

    val dummyUserFirestoreData = mapOf(
        FirestoreDatabase.Users.Fields.FULL_NAME to "Mansa Musa",
        FirestoreDatabase.Users.Fields.EMAIL to "some@mail.com",
        FirestoreDatabase.Users.Fields.PHOTO_URL to "https://someurl.com",
        FirestoreDatabase.Users.Fields.CREATED_AT to Timestamp(1697806449, 0)
    )

    val dummyCategory1 = Category(
        id = CategoryId("1"),
        name = "Category1"
    )

    val dummyCategory2 = Category(
        id = CategoryId("2"),
        name = "Category2"
    )

    val dummySection1 = Section(
        category = dummyCategory1,
        summaries = listOf(
            dummySummary1.copy(categories = listOf(CategoryId("1"))),
            dummySummary2.copy(categories = listOf(CategoryId("1")))
        )
    )

    val dummySection2 = Section(
        category = dummyCategory2,
        summaries = listOf(
            dummySummary1.copy(categories = listOf(CategoryId("2"))),
            dummySummary2.copy(categories = listOf(CategoryId("2")))
        )
    )

    val summariesForCategory1 = listOf(
        dummySummary1.copy(categories = listOf(CategoryId("1"))),
        dummySummary2.copy(categories = listOf(CategoryId("1"))),
    )

    val summariesForCategory2 = listOf(
        dummySummary1.copy(categories = listOf(CategoryId("2"))),
        dummySummary2.copy(categories = listOf(CategoryId("2"))),
    )
    val subscriptionPlan1 = SubscriptionPlan(
        identifier = "monthly",
        type = SubscriptionType.MONTHLY,
        productId = "monthlyProductId",
        price = Price(
            formattedValue = "$9.99",
            currency = "USD"
        )
    )
    val subscriptionPlan2 = SubscriptionPlan(
        identifier = "yearly",
        type = SubscriptionType.YEARLY,
        productId = "yearlyProductId",
        price = Price(
            formattedValue = "$99.99",
            currency = "USD"
        )
    )

    val audioTrack1 = AudioTrack(
        id = "1",
        url = "http://example.com/audio.mp3",
        title = "Content1",
        author = "Author1",
        coverUrl = "http://example.com/image.png"
    )

    val audioTrack2 = AudioTrack(
        id = "2",
        url = "http://example.com/audio.mp3",
        title = "Content2",
        author = "Author2",
        coverUrl = "http://example.com/image.png"
    )
}

package app.books.tanga.data

import app.books.tanga.data.user.toAnonymousUser
import app.books.tanga.data.user.toUser
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.FirebaseUserMetadata
import io.mockk.every
import io.mockk.mockk
import java.util.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class UserMapperTest {

    @Test
    fun `FirebaseUser toAnonymousUser should map correctly`() {
        val mockFirebaseUser: FirebaseUser = mockk(relaxed = true)
        val mockMetadata: FirebaseUserMetadata = mockk(relaxed = true)
        val creationTimestamp = 123456789L

        every { mockFirebaseUser.uid } returns "testUid"
        every { mockFirebaseUser.metadata } returns mockMetadata
        every { mockMetadata.creationTimestamp } returns creationTimestamp

        val result = mockFirebaseUser.toAnonymousUser()

        Assertions.assertEquals("testUid", result.id.value)
        Assertions.assertEquals("Anonymous", result.fullName)
        Assertions.assertEquals("", result.email)
        Assertions.assertNull(result.photoUrl)
        Assertions.assertFalse(result.isPro)
        Assertions.assertTrue(result.isAnonymous)
        Assertions.assertEquals(Date(creationTimestamp), result.createdAt)
    }

    @Test
    fun `FirebaseUser toUser should map correctly`() {
        val mockFirebaseUser: FirebaseUser = mockk(relaxed = true)
        val mockMetadata: FirebaseUserMetadata = mockk(relaxed = true)
        val creationTimestamp = 123456789L
        val displayName = "John Doe"
        val email = "johndoe@example.com"
        val photoUrl = "http://photo.url"

        every { mockFirebaseUser.uid } returns "testUid"
        every { mockFirebaseUser.displayName } returns displayName
        every { mockFirebaseUser.email } returns email
        every { mockFirebaseUser.photoUrl.toString() } returns photoUrl
        every { mockFirebaseUser.metadata } returns mockMetadata
        every { mockMetadata.creationTimestamp } returns creationTimestamp

        val result = mockFirebaseUser.toUser()

        Assertions.assertEquals("testUid", result.id.value)
        Assertions.assertEquals(displayName, result.fullName)
        Assertions.assertEquals(email, result.email)
        Assertions.assertEquals(photoUrl, result.photoUrl)
        Assertions.assertFalse(result.isPro)
        Assertions.assertEquals(Date(creationTimestamp), result.createdAt)
    }

    @Test
    fun `FirebaseUser toUser should throw if displayName is null`() {
        val mockFirebaseUser: FirebaseUser = mockk(relaxed = true)

        every { mockFirebaseUser.displayName } returns null

        assertThrows<IllegalArgumentException> {
            mockFirebaseUser.toUser()
        }
    }

    @Test
    fun `FirebaseUser toUser should throw if email is null`() {
        val mockFirebaseUser: FirebaseUser = mockk(relaxed = true)

        every { mockFirebaseUser.displayName } returns "John Doe"
        every { mockFirebaseUser.email } returns null

        assertThrows<IllegalArgumentException> {
            mockFirebaseUser.toUser()
        }
    }
}

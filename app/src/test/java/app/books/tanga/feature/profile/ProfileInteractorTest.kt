package app.books.tanga.feature.profile

import app.books.tanga.data.user.UserRepository
import app.books.tanga.entity.User
import app.books.tanga.fixtures.Fixtures
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class ProfileInteractorTest {

    private lateinit var userRepository: UserRepository
    private lateinit var profileInteractor: ProfileInteractor

    @BeforeEach
    fun setUp() {
        userRepository = mockk()
        profileInteractor = ProfileInteractor(userRepository)
    }

    @Test
    fun `getUserInfo returns user when retrieval is successful`() = runTest {
        // Given
        val user = Fixtures.dummyUser
        coEvery { userRepository.getUser() } returns Result.success(user)

        // When
        val result = profileInteractor.getUserInfo()

        // Then
        assertTrue(result.isSuccess)
        assertEquals(user, result.getOrNull())
    }

    @Test
    fun `getUserInfo returns error when user retrieval fails`() = runTest {
        // Given
        coEvery { userRepository.getUser() } returns Result.failure<User>(Exception("Error message"))

        // When
        val result = profileInteractor.getUserInfo()

        // Then
        assertTrue(result.isFailure)
    }
}

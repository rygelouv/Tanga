package app.books.tanga.feature

import app.books.tanga.data.user.UserRepository
import app.books.tanga.entity.User
import app.books.tanga.entity.UserId
import app.books.tanga.feature.auth.AuthenticationInteractor
import app.books.tanga.feature.profile.ProfileViewModel
import app.books.tanga.rule.MainCoroutineDispatcherExtension
import app.books.tanga.session.SessionState
import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import java.util.Date
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MainCoroutineDispatcherExtension::class)
@ExperimentalCoroutinesApi
@ExperimentalTime
class ProfileViewModelTest {

    private lateinit var authInteractor: AuthenticationInteractor
    private lateinit var userRepository: UserRepository
    private lateinit var viewModel: ProfileViewModel

    private val user = User(
        id = UserId("123456"),
        fullName = "John Doe",
        email = "john.doe@example.com",
        photoUrl = "https://example.com/johndoe.jpg",
        isPro = true,
        createdAt = Date()
    )

    @BeforeEach
    fun setUp() {
        authInteractor = mockk(relaxed = true)
        userRepository = mockk(relaxed = true)

        // Set up the userRepository to return the user object when getUser is called
        // Use the ofType matcher to specify the exact type expected for the return value
        coEvery { userRepository.getUser() } returns Result.success(user)

        viewModel = ProfileViewModel(authInteractor, userRepository)
    }

    @Test
    fun `Profile state is updated when user data is fetched successfully`() = runTest {
        // Assert that the state flow emits the correct data
        viewModel.state.test {
            val item = awaitItem()
            assert(item.fullName == user.fullName)
            assert(item.photoUrl == user.photoUrl)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onLogout triggers signOut in the authInteractor`() = runTest {
        // Set up the authInteractor to do nothing (already done by `relaxed = true`)
        coEvery { authInteractor.signOut() } returns Result.success(SessionState.SignedOut)

        // Call the onLogout function
        viewModel.onLogout()

        // Verify that signOut was called
        coVerify { authInteractor.signOut() }
    }
}

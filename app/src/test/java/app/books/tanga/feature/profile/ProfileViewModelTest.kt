package app.books.tanga.feature.profile

import app.books.tanga.data.user.UserRepository
import app.books.tanga.entity.User
import app.books.tanga.entity.UserId
import app.books.tanga.feature.auth.AuthenticationInteractor
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
import org.junit.jupiter.api.Assertions
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
    }

    @Test
    fun `Profile state is updated when user data is fetched successfully`() = runTest {
        // Set up the userRepository to return the user object when getUser is called
        // Use the ofType matcher to specify the exact type expected for the return value
        coEvery { userRepository.getUser() } returns Result.success(user)

        viewModel = ProfileViewModel(authInteractor, userRepository)

        // Assert that the state flow emits the correct data
        viewModel.state.test {
            val item = awaitItem()
            Assertions.assertTrue(item.userInfo?.fullName == user.fullName)
            Assertions.assertTrue(item.userInfo?.photoUrl == user.photoUrl)
            Assertions.assertFalse(item.userInfo?.isAnonymous == true)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onLogout triggers signOut in the authInteractor`() = runTest {
        coEvery { userRepository.getUser() } returns Result.success(user)

        // Set up the authInteractor to do nothing (already done by `relaxed = true`)
        coEvery { authInteractor.signOut() } returns Result.success(SessionState.SignedOut)

        viewModel = ProfileViewModel(authInteractor, userRepository)
        // Call the onLogout function
        viewModel.onLogout()

        // Verify that signOut was called
        coVerify { authInteractor.signOut() }
    }

    @Test
    fun `onProUpgrade triggers emission of NavigateToPricingPlan event`() = runTest {
        coEvery { userRepository.getUser() } returns Result.success(user)

        viewModel = ProfileViewModel(authInteractor, userRepository)
        // Call the onProUpgrade function
        viewModel.onProUpgrade()

        // Assert that the event flow emits the correct event
        viewModel.events.test {
            val event = expectMostRecentItem()
            Assertions.assertTrue(event is ProfileUiEvent.NavigateTo.ToPricingPlan)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onLogin triggers emission of NavigateToAuth event`() = runTest {
        coEvery { userRepository.getUser() } returns Result.success(user)

        viewModel = ProfileViewModel(authInteractor, userRepository)
        // Call the onLogin function
        viewModel.onLogin()

        // Assert that the event flow emits the correct event
        viewModel.events.test {
            val event = expectMostRecentItem()
            Assertions.assertTrue(event is ProfileUiEvent.NavigateTo.ToAuth)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Profile state is updated when user data is not fetched successfully`() = runTest {
        coEvery { userRepository.getUser() } returns Result.failure(Exception())

        viewModel = ProfileViewModel(authInteractor, userRepository)

        viewModel.state.test {
            val item = awaitItem()
            Assertions.assertNull(item.userInfo)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when user is anonymous state userInfo is null`() = runTest {
        coEvery { userRepository.getUser() } returns Result.success(null)

        viewModel = ProfileViewModel(authInteractor, userRepository)

        viewModel.state.test {
            val item = awaitItem()
            Assertions.assertNull(item.userInfo)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onLogout - when signOut fails`() = runTest {
        coEvery { userRepository.getUser() } returns Result.success(user)
        coEvery { authInteractor.signOut() } returns Result.failure(Exception())

        viewModel = ProfileViewModel(authInteractor, userRepository)
        viewModel.onLogout()

        viewModel.state.test {
            val item = awaitItem()
            Assertions.assertNotNull(item.userInfo)
            cancelAndIgnoreRemainingEvents()
        }
    }
}

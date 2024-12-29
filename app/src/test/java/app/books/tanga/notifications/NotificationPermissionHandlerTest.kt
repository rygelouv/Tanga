package app.books.tanga.notifications

import android.Manifest
import android.content.pm.PackageManager
import app.books.tanga.data.preferences.DefaultPrefDataStoreRepository
import app.books.tanga.utils.BuildVersionChecker
import app.books.tanga.utils.ResourcesProvider
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class NotificationPermissionHandlerTest {

    private val resourcesProvider: ResourcesProvider = mockk()
    private val dataStoreRepository: DefaultPrefDataStoreRepository = mockk()
    private val notificationPermissionHandler =
        NotificationPermissionHandlerImpl(resourcesProvider, dataStoreRepository)

    @Test
    fun `shouldRequestNotificationPermission should return false if SDK version is less than TIRAMISU`() = runBlocking {
        // Arrange
        mockkObject(BuildVersionChecker)
        every { BuildVersionChecker.isAtLeastTiramisu() } returns false

        // Act
        val result = notificationPermissionHandler.shouldRequestNotificationPermission(
            NotificationPermissionTrigger.SUMMARY_ACTION
        )

        // Assert
        assertFalse(result)
    }

    @Test
    fun `shouldRequestNotificationPermission should return false if permission is already granted`() = runBlocking {
        // Arrange
        mockkObject(BuildVersionChecker)
        every { BuildVersionChecker.isAtLeastTiramisu() } returns true
        every {
            resourcesProvider.getPermissionStatus(Manifest.permission.POST_NOTIFICATIONS)
        } returns PackageManager.PERMISSION_GRANTED

        // Act
        val result = notificationPermissionHandler.shouldRequestNotificationPermission(
            NotificationPermissionTrigger.SUMMARY_ACTION
        )

        // Assert
        assertFalse(result)
    }

    @Test
    fun `shouldRequestNotificationPermission should return true for SKIP_AUTH trigger if permission not granted`() = runBlocking {
        // Arrange
        mockkObject(BuildVersionChecker)
        every { BuildVersionChecker.isAtLeastTiramisu() } returns true
        every {
            resourcesProvider.getPermissionStatus(Manifest.permission.POST_NOTIFICATIONS)
        } returns PackageManager.PERMISSION_DENIED

        // Act
        val result = notificationPermissionHandler.shouldRequestNotificationPermission(
            NotificationPermissionTrigger.SKIP_AUTH
        )

        // Assert
        assertTrue(result)
    }

    @Test
    fun `shouldRequestNotificationPermission should return true for SUMMARY_ACTION trigger when counter is a multiple of 4`() = runBlocking {
        // Arrange
        mockkObject(BuildVersionChecker)
        every { BuildVersionChecker.isAtLeastTiramisu() } returns true
        every {
            resourcesProvider.getPermissionStatus(Manifest.permission.POST_NOTIFICATIONS)
        } returns PackageManager.PERMISSION_DENIED
        coEvery { dataStoreRepository.getNotificationTriggerCounter() } returns 3
        coEvery { dataStoreRepository.saveNotificationTriggerCounter(4) } returns Unit

        // Act
        val result = notificationPermissionHandler.shouldRequestNotificationPermission(
            NotificationPermissionTrigger.SUMMARY_ACTION
        )

        // Assert
        assertTrue(result)
    }

    @Test
    fun `shouldRequestNotificationPermission should return false for SUMMARY_ACTION trigger when counter is not a multiple of 4`() = runBlocking {
        // Arrange
        mockkObject(BuildVersionChecker)
        every { BuildVersionChecker.isAtLeastTiramisu() } returns true
        every {
            resourcesProvider.getPermissionStatus(Manifest.permission.POST_NOTIFICATIONS)
        } returns PackageManager.PERMISSION_DENIED
        coEvery { dataStoreRepository.getNotificationTriggerCounter() } returns 2
        coEvery { dataStoreRepository.saveNotificationTriggerCounter(3) } returns Unit

        // Act
        val result = notificationPermissionHandler.shouldRequestNotificationPermission(
            NotificationPermissionTrigger.SUMMARY_ACTION
        )

        // Assert
        assertFalse(result)
    }
}

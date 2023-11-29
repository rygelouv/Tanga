package app.books.tanga.feature.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.books.tanga.R
import app.books.tanga.coreui.components.ProfileImage
import app.books.tanga.coreui.components.TangaButtonLeftIcon
import app.books.tanga.coreui.theme.TangaTheme

@Composable
fun ProfileScreenContainer(
    onNavigateToAuth: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel(),
    onNavigateToPricing: () -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val event by viewModel.events.collectAsStateWithLifecycle(initialValue = ProfileUiEvent.Empty)

    HandleEvents(event = event, onNavigateToAuth = onNavigateToAuth, onNavigateToPricing = onNavigateToPricing)

    ProfileScreen(
        state = state,
        modifier = modifier,
        onProClick = { viewModel.onProUpgrade() },
        onLoginClick = { viewModel.onLogin() },
        onLogoutClick = { viewModel.onLogout() }
    )
}

@Composable
fun HandleEvents(
    event: ProfileUiEvent,
    onNavigateToAuth: () -> Unit,
    onNavigateToPricing: () -> Unit
) {
    when (event) {
        is ProfileUiEvent.NavigateTo.ToAuth -> {
            LaunchedEffect(Unit) {
                onNavigateToAuth()
            }
        }

        is ProfileUiEvent.NavigateTo.ToPricingPlan -> {
            LaunchedEffect(Unit) {
                onNavigateToPricing()
            }
        }

        else -> Unit
    }
}

@Composable
fun ProfileScreen(
    state: ProfileUiState,
    onLoginClick: () -> Unit,
    onLogoutClick: () -> Unit,
    modifier: Modifier = Modifier,
    onProClick: () -> Unit = {}
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 10.dp),
        floatingActionButtonPosition = FabPosition.Center
    ) {
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier =
            Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Column(
                modifier =
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                ProfileHeader(
                    modifier = Modifier,
                    fullName = state.fullName,
                    photoUrl = state.photoUrl,
                    onProClick = onProClick,
                    onLoginClick = onLoginClick,
                    isAnonymous = state.isAnonymous
                )
                Spacer(modifier = Modifier.height(70.dp))
                ProfileScreenBody(
                    modifier = Modifier,
                    onLogout = onLogoutClick,
                    isAnonymous = state.isAnonymous
                )
            }
        }
    }
}

@Composable
fun ProfileScreenBody(
    isAnonymous: Boolean,
    modifier: Modifier = Modifier,
    onLogout: () -> Unit
) {
    Surface(
        color = Color.White,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 60.dp, topEnd = 60.dp)
    ) {
        Column(
            modifier =
            Modifier
                .fillMaxSize()
                .padding(start = 10.dp, end = 10.dp, top = 30.dp, bottom = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            val openDialogState = remember { mutableStateOf(false) }
            val logout = remember { mutableStateOf(false) }

            ProfileContentAction(action = ProfileAction.CONTACT)
            ProfileContentAction(action = ProfileAction.PRIVACY_AND_TERMS)
            ProfileContentAction(action = ProfileAction.NOTIFICATIONS)
            if (isAnonymous.not()) {
                ProfileContentAction(action = ProfileAction.LOGOUT) {
                    openDialogState.value = true
                }
            }
            Spacer(modifier = Modifier.height(60.dp))

            if (openDialogState.value) {
                LogoutDialog(
                    onDismiss = { openDialogState.value = false },
                    onConfirm = {
                        openDialogState.value = false
                        logout.value = true
                    }
                )
            }

            LaunchedEffect(logout.value) {
                if (logout.value) onLogout()
            }
        }
    }
}

@Composable
fun ProfileHeader(
    fullName: String?,
    photoUrl: String?,
    isAnonymous: Boolean,
    onLoginClick: () -> Unit,
    modifier: Modifier = Modifier,
    onProClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 44.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(80.dp))
        ProfileImage(
            photoUrl = photoUrl,
            onClick = { }
        )

        Spacer(modifier = Modifier.height(20.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = fullName ?: stringResource(id = R.string.anonymous),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        Spacer(modifier = Modifier.height(30.dp))
        if (isAnonymous) {
            TangaButtonLeftIcon(
                text = stringResource(id = R.string.profile_create_account),
                rightIcon = R.drawable.ic_mobile_user,
                iconSize = 30.dp,
                onClick = onLoginClick
            )
        } else {
            ProButton { onProClick() }
        }
    }
}

@Preview
@Composable
private fun ProfileScreenPreview() {
    TangaTheme {
        ProfileScreen(
            state = ProfileUiState(
                fullName = "John Doe",
                photoUrl = "https://picsum.photos/200/300"
            ),
            onLoginClick = {},
            onLogoutClick = {}
        )
    }
}

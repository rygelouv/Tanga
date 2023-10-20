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

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel(),
    onProClick: () -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
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
                    onProClick = onProClick
                )
                Spacer(modifier = Modifier.height(70.dp))
                ProfileScreenContent {
                    viewModel.onLogout()
                }
            }
        }
    }
}

@Composable
fun ProfileScreenContent(modifier: Modifier = Modifier, onLogout: () -> Unit) {
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
            ProfileContentAction(action = ProfileAction.LOGOUT) {
                openDialogState.value = true
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
        Spacer(modifier = Modifier.height(10.dp))
        ProButton { onProClick() }
    }
}

@Preview
@Composable
private fun ProfileScreenPreview() {
    ProfileScreen()
}

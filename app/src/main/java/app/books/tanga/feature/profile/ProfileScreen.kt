package app.books.tanga.feature.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.books.tanga.R
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest

@Composable
fun ProfileScreen(viewModel: ProfileViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 10.dp)
            .verticalScroll(rememberScrollState()),
        floatingActionButtonPosition = FabPosition.Center
    ) {
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                ProfileHeader(
                    modifier = Modifier,
                    fullName = state.fullName,
                    photoUrl = state.photoUrl
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
fun ProfileScreenContent(onLogout: () -> Unit) {
    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 60.dp, topEnd = 60.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 10.dp, end = 10.dp, top = 30.dp, bottom = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            val openDialogState = remember { mutableStateOf(false) }
            val logout = remember { mutableStateOf(false) }

            ProfileContentAction(modifier = Modifier, ProfileAction.CONTACT)
            ProfileContentAction(modifier = Modifier, ProfileAction.PRIVACY_AND_TERMS)
            ProfileContentAction(modifier = Modifier, ProfileAction.NOTIFICATIONS)
            ProfileContentAction(modifier = Modifier, ProfileAction.LOGOUT) {
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
fun ProfileHeader(fullName: String?, photoUrl: String?, modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 44.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Spacer(modifier = Modifier.height(80.dp))
        ProfileImage(modifier = Modifier, photoUrl = photoUrl)

        Spacer(modifier = Modifier.height(20.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = fullName ?: stringResource(id = R.string.anonymous),
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        ProButton()
/*        Button(
            modifier = Modifier.height(34.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                backgroundColor = TangaBluePaleSemiTransparent,
            ),
            shape = RoundedCornerShape(20.dp),
            elevation = ButtonDefaults.elevation(0.dp, 0.dp),
            onClick = { *//*TODO*//* }
        ) {
            Text(
                text = "Freemium",
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
            )
        }*/
    }
}

@Composable
fun ProfileImage(modifier: Modifier, photoUrl: String?) {
    Surface(
        modifier = modifier.size(120.dp),
        shape = RoundedCornerShape(30.dp),
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
    ) {
        Image(
            painter = if (photoUrl != null) {
                rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(photoUrl)
                        .build()
                )
            } else { painterResource(id = R.drawable.profile_placeholder) },
            contentDescription = "profile picture",
            modifier = modifier.size(120.dp),
            contentScale = ContentScale.Crop
        )
    }
}

@Preview
@Composable
fun ProfileScreenPreview() {
    //val navController = rememberNavController()
    ProfileScreen()
}
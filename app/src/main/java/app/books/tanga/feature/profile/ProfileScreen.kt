package app.books.tanga.feature.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.books.tanga.R
import app.books.tanga.coreui.common.ExcludeFromJacocoGeneratedReport
import app.books.tanga.coreui.components.ProfileImage
import app.books.tanga.coreui.components.TangaButton
import app.books.tanga.coreui.theme.LocalSpacing
import app.books.tanga.coreui.theme.Shapes
import app.books.tanga.coreui.theme.TangaTheme
import app.books.tanga.coreui.theme.extraExtraExtraLarge
import app.books.tanga.utils.openLinkInCustomTab

private const val CONTACT_URL = "https://form.jotform.com/242065602713550"

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
    onSettingsClick: () -> Unit,
    onPrivacyAndTermsClick: () -> Unit,
    modifier: Modifier = Modifier,
    onProClick: () -> Unit
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 2.dp),
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
                    userInfo = state.userInfo,
                    onProClick = onProClick,
                    onLoginClick = onLoginClick
                )
                Spacer(modifier = Modifier.weight(1f))
                ProfileScreenBody(
                    userInfo = state.userInfo,
                    modifier = Modifier,
                    onSettingsClick = onSettingsClick,
                    onPrivacyAndTermsClick = onPrivacyAndTermsClick
                )
            }
        }
    }
}

/**
 * Note: Improve state handling with viewmodel
 */
@Composable
fun ProfileScreenBody(
    userInfo: UserInfoUi?,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier,
    onPrivacyAndTermsClick: () -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.onPrimary,
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
            val context = LocalContext.current
            ProfileContentAction(action = ProfileAction.CONTACT, onClick = {
                openLinkInCustomTab(context, CONTACT_URL)
            })
            ProfileContentAction(action = ProfileAction.PRIVACY_AND_TERMS) {
                onPrivacyAndTermsClick()
            }
            if (userInfo != null) {
                ProfileContentAction(action = ProfileAction.SETTING) {
                    onSettingsClick()
                }
            }
        }
    }
}

@Composable
fun ProfileHeader(
    userInfo: UserInfoUi?,
    onLoginClick: () -> Unit,
    modifier: Modifier = Modifier,
    onProClick: () -> Unit
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
            photoUrl = userInfo?.photoUrl,
            onClick = { }
        )

        Spacer(modifier = Modifier.height(20.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = userInfo?.fullName ?: stringResource(id = R.string.anonymous),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        Spacer(modifier = Modifier.height(30.dp))
        MainCtaArea(userInfo = userInfo, onLoginClick = onLoginClick, onProClick = onProClick)
    }
}

@Composable
fun MainCtaArea(userInfo: UserInfoUi?, onLoginClick: () -> Unit, onProClick: () -> Unit) {
    when {
        userInfo == null -> {
            TangaButton(
                text = stringResource(id = R.string.profile_create_account),
                onClick = onLoginClick
            )
        }
        userInfo.subscriberInfo?.hasActiveSubscription == true -> {
            PremiumAccountTag()
        }
        else -> ProButton { onProClick() }
    }
}

@Composable
fun PremiumAccountTag(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.2f),
                shape = Shapes.extraExtraExtraLarge
            )
            .clickable {}
            .padding(horizontal = LocalSpacing.current.medium, vertical = LocalSpacing.current.small),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(24.dp).testTag("search_icon"),
            painter = painterResource(id = R.drawable.ic_crown),
            contentDescription = "search icon",
            tint = MaterialTheme.colorScheme.tertiary
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = stringResource(id = R.string.premium_user),
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.tertiary,
            textAlign = TextAlign.Center,
        )
    }
}

@Preview
@Composable
@ExcludeFromJacocoGeneratedReport
private fun ProfileScreenPreview() {
    TangaTheme {
        ProfileScreen(
            state = ProfileUiState(
                userInfo = UserInfoUi(
                    fullName = "John Doe",
                    photoUrl = "https://picsum.photos/200/300",
                    isAnonymous = false
                )
            ),
            onLoginClick = {},
            onSettingsClick = {},
            onPrivacyAndTermsClick = {},
            onProClick = {}
        )
    }
}

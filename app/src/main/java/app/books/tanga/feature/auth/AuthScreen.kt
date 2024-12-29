package app.books.tanga.feature.auth

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.books.tanga.R
import app.books.tanga.common.ui.ProgressState
import app.books.tanga.coreui.common.ExcludeFromJacocoGeneratedReport
import app.books.tanga.coreui.components.DotsAnimation
import app.books.tanga.coreui.theme.LocalSpacing
import app.books.tanga.coreui.theme.TangaTheme

@Composable
fun AuthScreen(
    state: AuthUiState,
    events: AuthUiEvent,
    onAuthSkip: () -> Unit,
    onNavigateToNotificationPermissionScreen: () -> Unit,
    onClose: () -> Unit,
    onTermsAndPrivacyClick: () -> Unit,
    onAuthSuccess: () -> Unit,
    modifier: Modifier = Modifier,
    onGoogleSignInButtonClick: () -> Unit = {},
    onGoogleSignInComplete: (Intent) -> Unit = {},
    onGoogleSignInNotComplete: () -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = { AuthTopBar(state, onAuthSkip) }
    ) { paddingValues ->
        AuthContent(
            modifier = Modifier.padding(paddingValues),
            state = state,
            events = events,
            onAuthSuccess = onAuthSuccess,
            onGoogleSignInButtonClick = onGoogleSignInButtonClick,
            onGoogleSignInComplete = onGoogleSignInComplete,
            onGoogleSignInNotComplete = onGoogleSignInNotComplete,
            onTermsAndPrivacyClick = onTermsAndPrivacyClick,
            onClose = onClose,
            onNavigateToNotificationPermissionScreen = onNavigateToNotificationPermissionScreen
        )
    }
}

@Composable
private fun AuthTopBar(
    state: AuthUiState,
    onAuthSkip: () -> Unit
) {
    Row(
        modifier = Modifier.padding(
            top = 5.dp,
            start = 5.dp,
            end = LocalSpacing.current.extraLarge,
            bottom = 5.dp
        ),
        horizontalArrangement = Arrangement.End
    ) {
        Spacer(modifier = Modifier.weight(7f))
        SkipButton(
            skipState = state.skipProgressState,
            skipText = state.skipText,
            onSkip = onAuthSkip
        )
    }
}

@Composable
private fun RowScope.SkipButton(
    skipState: ProgressState,
    skipText: Int,
    onSkip: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onSkip,
        modifier = modifier
            .width(120.dp)
            .weight(3f),
        colors = ButtonDefaults.buttonColors(
            contentColor = MaterialTheme.colorScheme.tertiary,
            containerColor = MaterialTheme.colorScheme.tertiaryContainer
        ),
        shape = RoundedCornerShape(20.dp),
        elevation = ButtonDefaults.buttonElevation(0.dp)
    ) {
        when (skipState) {
            ProgressState.Hide -> Text(
                text = stringResource(id = skipText),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold
            )
            ProgressState.Show -> DotsAnimation(
                modifier = Modifier.testTag("ProgressIndicator")
            )
        }
    }
}

@Composable
fun AuthContent(
    state: AuthUiState,
    events: AuthUiEvent,
    onGoogleSignInButtonClick: () -> Unit,
    onAuthSuccess: () -> Unit,
    onNavigateToNotificationPermissionScreen: () -> Unit,
    onClose: () -> Unit,
    onTermsAndPrivacyClick: () -> Unit,
    onGoogleSignInComplete: (Intent) -> Unit,
    modifier: Modifier = Modifier,
    onGoogleSignInNotComplete: () -> Unit
) {
    SignIn(
        onAuthSuccess = onAuthSuccess,
        event = events,
        onGoogleSignInComplete = onGoogleSignInComplete,
        onGoogleSignInNotComplete = onGoogleSignInNotComplete,
        onClose = onClose,
        onNavigateToNotificationPermissionScreen = onNavigateToNotificationPermissionScreen,
    )

    Column(
        modifier =
        modifier
            .fillMaxSize()
            .padding(20.dp).verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            modifier =
            Modifier
                .weight(3f)
                .padding(horizontal = 10.dp),
            painter = painterResource(id = R.drawable.graphic_pricing),
            contentDescription = "app icon"
        )
        WelcomeMessageColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )

        // Google Sign In button
        GoogleSignInButton(state = state, onClick = onGoogleSignInButtonClick)
        TermsAndPrivacyText(onTermsAndPrivacyClick = onTermsAndPrivacyClick)
    }
}

@Composable
fun TermsAndPrivacyText(
    onTermsAndPrivacyClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val annotatedString = buildAnnotatedString {
        append(stringResource(id = R.string.auth_terms_and_privacy_prefix_text))
        append(" ")

        pushStringAnnotation(
            tag = "TermsOfService",
            annotation = "terms"
        )
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.primary,
                textDecoration = TextDecoration.Underline
            )
        ) {
            append(stringResource(id = R.string.auth_terms_and_conditions))
        }
        append(" ")
        pop()

        append(stringResource(id = R.string.auth_terms_and_privacy_middle_text))
        append(" ")

        // Privacy Policy
        pushStringAnnotation(
            tag = "PrivacyPolicy",
            annotation = "privacy"
        )
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.primary,
                textDecoration = TextDecoration.Underline
            )
        ) {
            append(stringResource(id = R.string.auth_privacy_policy))
        }
        pop()
    }

    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        Text(
            text = annotatedString,
            fontSize = 12.sp,
            modifier = Modifier.clickable {
                onTermsAndPrivacyClick()
            },
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.outline,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun WelcomeMessageColumn(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.auth_welcome_to_tanga),
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 41.dp)
                .padding(top = 10.dp)
                .weight(1f),
            color = MaterialTheme.colorScheme.outline,
            text = stringResource(id = R.string.auth_sign_up_with_google_message),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
@ExcludeFromJacocoGeneratedReport
private fun AuthScreenPreview() {
    val state = AuthUiState(
        googleSignInButtonProgressState = ProgressState.Hide
    )
    val events = AuthUiEvent.Empty
    TangaTheme {
        AuthScreen(
            onAuthSkip = {},
            onClose = {},
            onTermsAndPrivacyClick = {},
            onAuthSuccess = {},
            state = state,
            events = events,
            onGoogleSignInNotComplete = {},
            onNavigateToNotificationPermissionScreen = {}
        )
    }
}

package app.books.tanga.feature.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import app.books.tanga.coreui.components.SimpleTopBar
import app.books.tanga.utils.openLinkInCustomTab

private const val PRIVACY_POLICY_URL = "https://tanga.app/privacy_policy.html"
private const val TERMS_AND_CONDITIONS_URL = "https://tanga.app/terms_and_conditions.html"

@Composable
fun PrivacyAndTermsScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            SimpleTopBar(onNavigateBack)
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val context = LocalContext.current
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                ProfileContentAction(action = ProfileAction.PRIVACY_POLICY) {
                    openLinkInCustomTab(context, PRIVACY_POLICY_URL)
                }
            }
            Spacer(
                modifier = Modifier
                    .height(1.dp)
                    .background(MaterialTheme.colorScheme.onSurfaceVariant)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                ProfileContentAction(action = ProfileAction.TERMS_AND_CONDITIONS) {
                    openLinkInCustomTab(context, TERMS_AND_CONDITIONS_URL)
                }
            }
        }
    }
}

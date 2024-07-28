package app.books.tanga.feature.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import app.books.tanga.coreui.icons.TangaIcons
import app.books.tanga.utils.openLinkInCustomTab

private const val PRIVACY_POLICY_URL = "https://tanga.app/privacy_policy.html"
private const val TERMS_AND_CONDITIONS_URL = "https://tanga.app/terms_and_conditions.html"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivacyAndTermsScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                modifier = modifier
                    .shadow(elevation = 1.dp),
                title = {},
                navigationIcon = {
                    IconButton(onClick = { onNavigateBack() }) {
                        Icon(
                            modifier = Modifier.size(26.dp),
                            painter = painterResource(id = TangaIcons.LeftArrow),
                            tint = MaterialTheme.colorScheme.onTertiaryContainer,
                            contentDescription = "back navigation"
                        )
                    }
                }
            )
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
            Spacer(modifier = Modifier.height(1.dp).background(MaterialTheme.colorScheme.onSurfaceVariant))
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

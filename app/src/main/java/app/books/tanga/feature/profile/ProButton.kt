package app.books.tanga.feature.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.books.tanga.R
import app.books.tanga.coreui.theme.LocalGradientColors
import app.books.tanga.coreui.theme.button

@Composable
fun ProButton(onClick: () -> Unit = {}) {
    val gradientColors =
        listOf(
            LocalGradientColors.current.start,
            LocalGradientColors.current.center,
            LocalGradientColors.current.end
        )

    Row(
        modifier =
        Modifier
            .height(70.dp)
            .background(
                brush = Brush.linearGradient(colors = gradientColors),
                shape = RoundedCornerShape(40.dp)
            ).clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(12.dp))
        Box(
            contentAlignment = Alignment.Center,
            modifier =
            Modifier
                .size(52.dp)
                .clip(CircleShape)
                .background(color = Color.White)
                .padding(all = 10.dp)
        ) {
            Icon(
                modifier = Modifier.size(36.dp),
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_premium),
                contentDescription = "action icon",
                tint = Color.Unspecified
            )
        }

        Text(
            text = stringResource(id = R.string.profile_upgrade_to_pro),
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.button,
            textAlign = TextAlign.Center,
            color = Color.White
        )
    }
}

@Preview
@Composable
fun ProButtonPreview() {
    ProButton()
}

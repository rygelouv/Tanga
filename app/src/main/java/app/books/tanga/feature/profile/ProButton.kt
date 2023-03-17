package app.books.tanga.feature.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.books.tanga.R
import app.books.tanga.ui.theme.TangaBlueDark
import app.books.tanga.ui.theme.TangaBluePale
import app.books.tanga.ui.theme.TangaLightBlue
import app.books.tanga.ui.theme.TangaWhiteBackground

@Composable
fun ProButton() {
    val gradientColors = listOf(TangaBlueDark, TangaBluePale, TangaLightBlue)
    Button(
        modifier = Modifier
            .defaultMinSize(minWidth = 1.dp, minHeight = 1.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = TangaWhiteBackground,
            
            contentColor = Color.Unspecified
        ),
        onClick = {},
        elevation = ButtonDefaults.elevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(40.dp)
    ) {
        Row(
            modifier = Modifier
                .height(70.dp)
                .background(
                    brush = Brush.linearGradient(colors = gradientColors),
                    shape = RoundedCornerShape(40.dp)
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(12.dp))
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(color = Color.White)
                    .padding(all = 10.dp),
            ) {
                Icon(
                    modifier = Modifier.size(36.dp),
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_premium),
                    contentDescription = "action icon",
                )
            }

            Text(
                text = "Upgrade to Tanga Pro",
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.h4,
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview
@Composable
fun ProButtonPreview() {
    ProButton()
}
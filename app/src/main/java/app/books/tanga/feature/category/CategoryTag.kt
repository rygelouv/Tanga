package app.books.tanga.feature.category

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.books.tanga.R
import app.books.tanga.common.ui.Tag
import app.books.tanga.ui.theme.LocalTintColor

@Composable
fun CategoryTag(icon: Int, categoryName: String) {
    Tag(
        modifier = Modifier.background(
            color = Color.White,
            shape = RoundedCornerShape(12.dp)
        ),
        text = categoryName,
        icon = icon,
        tint = LocalTintColor.current.color
    )
}

@Composable
@Preview
fun CategoryTagPreview() {
    CategoryTag(
        icon = R.drawable.ic_indicator_mindmap ,
        categoryName = "Business"
    )
}
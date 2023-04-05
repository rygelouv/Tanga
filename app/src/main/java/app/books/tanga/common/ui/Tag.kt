package app.books.tanga.common.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import app.books.tanga.ui.theme.LocalSpacing

@Composable
fun Tag(modifier: Modifier, text: String, icon: Int, tint: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(start = 10.dp, end = 10.dp),
    ) {
        Icon(
            modifier = Modifier.size(13.dp),
            painter = painterResource(id = icon),
            contentDescription = null,

            tint = tint
        )
        Spacer(modifier = Modifier.width(LocalSpacing.current.small))
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
            color = tint,
            modifier = Modifier.padding(top = LocalSpacing.current.extraSmall)
        )
    }
}
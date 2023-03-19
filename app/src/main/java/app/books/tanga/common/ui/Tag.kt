package app.books.tanga.common.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = text,
            fontSize = 12.sp,
            color = tint,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}
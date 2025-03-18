package app.books.tanga.feature.aiprompts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import app.books.tanga.coreui.theme.LocalSpacing
import app.books.tanga.feature.audioplayer.miniplayer.MiniPlayerAwareSpacer
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.immutableListOf

@Composable
fun QuotesView(
    quotes: ImmutableList<QuoteExplanation>,
    modifier: Modifier = Modifier
) {
    // val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = MaterialTheme.colorScheme.onPrimaryContainer)
    ) {
        quotes.forEach { quote ->
            QuoteView(quote = quote)
            Spacer(modifier = Modifier.height(LocalSpacing.current.large))
        }
        MiniPlayerAwareSpacer()
    }
}

@Composable
fun QuoteView(
    quote: QuoteExplanation,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "\"${quote.quote}\"",
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.tertiary,
            fontSize = 16.sp,
            lineHeight = 26.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(LocalSpacing.current.small))
        Text(
            text = quote.explanation,
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            fontSize = 14.sp,
            lineHeight = 24.sp
        )
    }
}

@Composable
@Preview
private fun QuotesViewPreview() {
    QuotesView(
        quotes = immutableListOf(
            QuoteExplanation(
                quote = "You don’t need to know your passion to design a life you love.",
                explanation = "This challenges the common advice of 'finding your passion' " +
                    "and suggests that passion can be cultivated through exploration and engagement " +
                    "rather than being a prerequisite for a fulfilling life."
            )
        )
    )
}

package app.books.tanga.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.books.tanga.R
import app.books.tanga.common.FakeData
import app.books.tanga.feature.auth.HomeTopCard
import app.books.tanga.feature.summary.SummaryRow
import app.books.tanga.ui.theme.TangaBlueDark
import app.books.tanga.ui.theme.TangaBluePale
import app.books.tanga.ui.theme.TangaLightGray2
import app.books.tanga.ui.theme.TangaWhiteBackground

@Composable
fun HomeScreen() {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(color = TangaWhiteBackground)
            .padding(14.dp),
        topBar = { HomeTopBar() },
    ) {
        HomeContent(modifier = Modifier.padding(it), userFirstName = "Rygel")
    }
}

@Composable
fun HomeTopBar() {
    TopAppBar(
        elevation = 0.dp,
        backgroundColor = TangaWhiteBackground,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "home search icon",
                tint = TangaLightGray2
            )
            Spacer(modifier = Modifier.weight(5f))
            Surface(
                modifier = Modifier.size(40.dp),
                shape = CircleShape,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.jessica_felicio_image),
                    contentDescription = "home profile picture",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Composable
fun HomeContent(modifier: Modifier, userFirstName: String) {
    val dailySummary = remember {
        FakeData.allSummaries().first()
    }
    Column(modifier = modifier.background(color = TangaWhiteBackground)) {
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            color = TangaLightGray2,
            text = "Welcome back, $userFirstName",
            fontSize = 18.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(6.dp))
        LazyColumn(modifier.fillMaxSize()) {
            item {
                HomeTopCard(dailySummary)
            }
            item {
                HomeSection(modifier = modifier, sectionTitle = "Personal Growth", isFirst = true)
            }
            item {
                HomeSection(modifier = modifier, "Financial education")
            }
            item {
                HomeSection(modifier = modifier, "Business")
            }
            item {
                HomeSection(modifier = modifier, "Psychology")
            }
        }
    }
}

@Composable
fun HomeSection(modifier: Modifier, sectionTitle: String, isFirst: Boolean = false) {
    Column {
        Spacer(modifier = Modifier.height(if (isFirst) 22.dp else 28.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = modifier.weight(2f),
                text = sectionTitle,
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.h4,
                color = TangaBlueDark,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = stringResource(id = R.string.home_see_all),
                color = TangaBluePale,
                textAlign = TextAlign.End,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(22.dp))
        SummaryRow(FakeData.allSummaries().shuffled().take(4))
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}